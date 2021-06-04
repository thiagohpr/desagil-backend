package br.edu.insper.desagil.backend.core.firestore;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldPath;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteBatch;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import br.edu.insper.desagil.backend.core.DatabaseDAO;
import br.edu.insper.desagil.backend.core.exception.DatabaseException;
import br.edu.insper.desagil.backend.core.exception.InternalException;
import br.edu.insper.desagil.backend.core.firestore.exception.FirestoreExecutionException;
import br.edu.insper.desagil.backend.core.firestore.exception.FirestoreInterruptedException;

public abstract class FirestoreDAO<T extends FirestoreObject> implements DatabaseDAO<String, T> {
	private final Class<T> klass;
	private final Firestore firestore;
	private final CollectionReference collection;

	@SuppressWarnings("unchecked")
	public FirestoreDAO(String path) {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		Type[] types = type.getActualTypeArguments();
		this.klass = (Class<T>) types[0];

		this.firestore = FirestoreClient.getFirestore();

		this.collection = this.firestore.collection(path);
	}

	private final List<T> execute(Query query) throws DatabaseException {
		QuerySnapshot documents;
		try {
			documents = query.get().get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		List<T> values = new ArrayList<>();
		for (DocumentSnapshot document : documents) {
			values.add(document.toObject(klass));
		}
		return values;
	}

	@Override
	public boolean exists(String key) throws DatabaseException {
		if (key == null) {
			throw new InternalException("Key cannot be null");
		}
		if (key.isBlank()) {
			throw new InternalException("Key cannot be blank");
		}
		DocumentSnapshot document;
		try {
			document = collection.document(key).get().get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		return document.exists();
	}

	@Override
	public boolean exists(List<String> keys) throws DatabaseException {
		if (keys == null) {
			throw new InternalException("List of keys cannot be null");
		}
		if (keys.isEmpty()) {
			throw new InternalException("List of keys cannot be empty");
		}
		for (String key : keys) {
			if (key == null) {
				throw new InternalException("Key cannot be null");
			}
			if (key.isBlank()) {
				throw new InternalException("Key cannot be blank");
			}
		}
		QuerySnapshot documents;
		Query query = collection.whereIn(FieldPath.documentId(), keys);
		try {
			documents = query.get().get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		return keys.size() == documents.size();
	}

	@Override
	public Date create(T value) throws DatabaseException {
		if (value == null) {
			throw new InternalException("Value cannot be null");
		}
		WriteResult result;
		try {
			DocumentReference document;
			if (value instanceof AutokeyFirestoreObject) {
				document = collection.add(value).get();
				((AutokeyFirestoreObject) value).setKey(document.getId());
			} else {
				String key = value.key();
				if (key == null) {
					throw new InternalException("Key cannot be null");
				}
				if (key.isBlank()) {
					throw new InternalException("Key cannot be blank");
				}
				document = collection.document(key);
				if (document.get().get().exists()) {
					throw new InternalException("Key " + key + " already exists");
				}
			}
			result = document.set(value).get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		return result.getUpdateTime().toDate();
	}

	@Override
	public T retrieve(String key) throws DatabaseException {
		if (key == null) {
			throw new InternalException("Key cannot be null");
		}
		if (key.isBlank()) {
			throw new InternalException("Key cannot be blank");
		}
		DocumentSnapshot document;
		try {
			document = collection.document(key).get().get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		if (!document.exists()) {
			throw new InternalException("Key " + key + " not found");
		}
		return document.toObject(klass);
	}

	@Override
	public List<T> retrieve(List<String> keys) throws DatabaseException {
		if (keys == null) {
			throw new InternalException("List of keys cannot be null");
		}
		if (keys.isEmpty()) {
			throw new InternalException("List of keys cannot be empty");
		}
		for (String key : keys) {
			if (key == null) {
				throw new InternalException("Key cannot be null");
			}
			if (key.isBlank()) {
				throw new InternalException("Key cannot be blank");
			}
		}
		return execute(collection.whereIn(FieldPath.documentId(), keys));
	}

	@Override
	public List<T> retrieveLt(String name, Object value) throws DatabaseException {
		if (name == null) {
			throw new InternalException("Name cannot be null");
		}
		if (name.isBlank()) {
			throw new InternalException("Name cannot be blank");
		}
		return execute(collection.whereLessThan(name, value));
	}

	@Override
	public List<T> retrieveLeq(String name, Object value) throws DatabaseException {
		if (name == null) {
			throw new InternalException("Name cannot be null");
		}
		if (name.isBlank()) {
			throw new InternalException("Name cannot be blank");
		}
		return execute(collection.whereLessThanOrEqualTo(name, value));
	}

	@Override
	public List<T> retrieveEq(String name, Object value) throws DatabaseException {
		if (name == null) {
			throw new InternalException("Name cannot be null");
		}
		if (name.isBlank()) {
			throw new InternalException("Name cannot be blank");
		}
		return execute(collection.whereEqualTo(name, value));
	}

	@Override
	public List<T> retrieveGt(String name, Object value) throws DatabaseException {
		if (name == null) {
			throw new InternalException("Name cannot be null");
		}
		if (name.isBlank()) {
			throw new InternalException("Name cannot be blank");
		}
		return execute(collection.whereGreaterThan(name, value));
	}

	@Override
	public List<T> retrieveGeq(String name, Object value) throws DatabaseException {
		if (name == null) {
			throw new InternalException("Name cannot be null");
		}
		if (name.isBlank()) {
			throw new InternalException("Name cannot be blank");
		}
		return execute(collection.whereGreaterThanOrEqualTo(name, value));
	}

	@Override
	public List<T> retrieveIn(String name, List<Object> values) throws DatabaseException {
		if (name == null) {
			throw new InternalException("Name cannot be null");
		}
		if (name.isBlank()) {
			throw new InternalException("Name cannot be blank");
		}
		if (values == null) {
			throw new InternalException("List of values cannot be null");
		}
		if (values.isEmpty()) {
			throw new InternalException("List of values cannot be empty");
		}
		return execute(collection.whereIn(name, values));
	}

	@Override
	public List<T> retrieveAll() throws DatabaseException {
		List<T> values = new ArrayList<>();
		for (DocumentReference document : collection.listDocuments()) {
			DocumentSnapshot snapshot;
			try {
				snapshot = document.get().get();
			} catch (ExecutionException exception) {
				throw new FirestoreExecutionException(exception);
			} catch (InterruptedException exception) {
				throw new FirestoreInterruptedException(exception);
			}
			values.add(snapshot.toObject(klass));
		}
		return values;
	}

	@Override
	public Date update(T value) throws DatabaseException {
		if (value == null) {
			throw new InternalException("Value cannot be null");
		}
		String key = value.key();
		if (key == null) {
			throw new InternalException("Key cannot be null");
		}
		if (key.isBlank()) {
			throw new InternalException("Key cannot be blank");
		}
		DocumentReference document = collection.document(key);
		WriteResult result;
		try {
			if (!document.get().get().exists()) {
				throw new InternalException("Key " + key + " not found");
			}
			result = document.set(value).get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		return result.getUpdateTime().toDate();
	}

	@Override
	public Date delete(String key) throws DatabaseException {
		if (key == null) {
			throw new InternalException("Key cannot be null");
		}
		if (key.isBlank()) {
			throw new InternalException("Key cannot be blank");
		}
		WriteResult result;
		DocumentReference document = collection.document(key);
		try {
			if (!document.get().get().exists()) {
				throw new InternalException("Key " + key + " not found");
			}
			result = document.delete().get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		return result.getUpdateTime().toDate();
	}

	@Override
	public List<Date> delete(List<String> keys) throws DatabaseException {
		if (keys == null) {
			throw new InternalException("List of keys cannot be null");
		}
		if (keys.isEmpty()) {
			throw new InternalException("List of keys cannot be empty");
		}
		WriteBatch batch = firestore.batch();
		for (String key : keys) {
			if (key == null) {
				throw new InternalException("Key cannot be null");
			}
			if (key.isBlank()) {
				throw new InternalException("Key cannot be blank");
			}
			batch.delete(collection.document(key));
		}
		List<WriteResult> results;
		try {
			results = batch.commit().get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		List<Date> dates = new ArrayList<>();
		for (WriteResult result : results) {
			dates.add(result.getUpdateTime().toDate());
		}
		return dates;
	}

	@Override
	public List<Date> deleteAll() throws DatabaseException {
		WriteBatch batch = firestore.batch();
		for (DocumentReference document : collection.listDocuments()) {
			batch.delete(document);
		}
		List<WriteResult> results;
		try {
			results = batch.commit().get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		List<Date> dates = new ArrayList<>();
		for (WriteResult result : results) {
			dates.add(result.getUpdateTime().toDate());
		}
		return dates;
	}
}

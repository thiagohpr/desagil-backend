package br.edu.insper.desagil.backend.core;

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
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

import br.edu.insper.desagil.backend.core.exception.APIException;
import br.edu.insper.desagil.backend.core.exception.DBException;
import br.edu.insper.desagil.backend.core.exception.FirestoreExecutionException;
import br.edu.insper.desagil.backend.core.exception.FirestoreInterruptedException;
import br.edu.insper.desagil.backend.core.exception.NotFoundException;

public class FirestoreDAO<T extends FirestoreEntity> implements DAO<String, T> {
	private final Class<T> klass;
	private final CollectionReference collection;

	@SuppressWarnings("unchecked")
	public FirestoreDAO(String path) throws APIException {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		Type[] types = type.getActualTypeArguments();
		this.klass = (Class<T>) types[0];

		Firestore firestore = FirestoreClient.getFirestore();
		try {
			this.collection = firestore.collection(path);
		} catch (IllegalArgumentException exception) {
			throw new NotFoundException("Collection " + path + " not found");
		}
	}

	private final List<T> execute(Query query) throws DBException, APIException {
		List<T> values = new ArrayList<>();
		QuerySnapshot documents;
		try {
			documents = query.get().get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		for (DocumentSnapshot document : documents) {
			values.add(document.toObject(klass));
		}
		return values;
	}

	public boolean exists(String key) throws DBException, APIException {
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

	public boolean exists(List<String> keys) throws DBException, APIException {
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
	public Date create(T value) throws DBException, APIException {
		WriteResult result;
		try {
			result = collection.document(value.key()).set(value).get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		return result.getUpdateTime().toDate();
	}

	@Override
	public T retrieve(String key) throws DBException, APIException {
		DocumentSnapshot document;
		try {
			document = collection.document(key).get().get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		if (!document.exists()) {
			throw new NotFoundException("Key " + key + " not found");
		}
		return document.toObject(klass);
	}

	@Override
	public List<T> retrieve(List<String> keys) throws DBException, APIException {
		return execute(collection.whereIn(FieldPath.documentId(), keys));
	}

	@Override
	public List<T> retrieveLt(String key, Object value) throws DBException, APIException {
		return execute(collection.whereLessThan(key, value));
	}

	@Override
	public List<T> retrieveLeq(String key, Object value) throws DBException, APIException {
		return execute(collection.whereLessThanOrEqualTo(key, value));
	}

	@Override
	public List<T> retrieveEq(String key, Object value) throws DBException, APIException {
		return execute(collection.whereEqualTo(key, value));
	}

	@Override
	public List<T> retrieveGt(String key, Object value) throws DBException, APIException {
		return execute(collection.whereGreaterThan(key, value));
	}

	@Override
	public List<T> retrieveGeq(String key, Object value) throws DBException, APIException {
		return execute(collection.whereGreaterThanOrEqualTo(key, value));
	}

	@Override
	public List<T> retrieveIn(String key, List<Object> values) throws DBException, APIException {
		return execute(collection.whereIn(key, values));
	}

	@Override
	public List<T> retrieveAll() throws DBException, APIException {
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
	public Date update(T value) throws DBException, APIException {
		WriteResult result;
		String key = value.key();
		try {
			DocumentSnapshot document = collection.document(key).get().get();
			if (!document.exists()) {
				throw new NotFoundException("Key " + key + " not found");
			}
			result = collection.document(key).set(value).get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		return result.getUpdateTime().toDate();
	}

	@Override
	public Date delete(String key) throws DBException, APIException {
		WriteResult result;
		try {
			DocumentSnapshot document = collection.document(key).get().get();
			if (!document.exists()) {
				throw new NotFoundException("Key " + key + " not found");
			}
			result = collection.document(key).delete().get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		return result.getUpdateTime().toDate();
	}
}

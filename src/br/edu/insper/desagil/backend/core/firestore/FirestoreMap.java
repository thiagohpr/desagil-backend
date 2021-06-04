package br.edu.insper.desagil.backend.core.firestore;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;

import br.edu.insper.desagil.backend.core.DatabaseMap;
import br.edu.insper.desagil.backend.core.exception.DatabaseException;
import br.edu.insper.desagil.backend.core.exception.InternalException;
import br.edu.insper.desagil.backend.core.firestore.exception.FirestoreExecutionException;
import br.edu.insper.desagil.backend.core.firestore.exception.FirestoreInterruptedException;

public final class FirestoreMap implements DatabaseMap<String> {
	private static final String NAME = "maps";

	private DocumentReference document;

	public FirestoreMap(String name) throws DatabaseException {
		if (name == null) {
			throw new InternalException("Name cannot be null");
		}
		if (name.isBlank()) {
			throw new InternalException("Name cannot be blank");
		}

		Firestore firestore = FirestoreClient.getFirestore();

		CollectionReference collection = firestore.collection(NAME);

		DocumentSnapshot snapshot;
		this.document = collection.document(name);
		try {
			snapshot = this.document.get().get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		if (!snapshot.exists()) {
			this.document.create(new HashMap<>());
		}
	}

	@Override
	public final boolean has(String key) throws DatabaseException {
		if (key == null) {
			throw new InternalException("Key cannot be null");
		}
		if (key.isBlank()) {
			throw new InternalException("Key cannot be blank");
		}
		DocumentSnapshot snapshot;
		try {
			snapshot = document.get().get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		return snapshot.contains(key);
	}

	@Override
	public final Object get(String key) throws DatabaseException {
		if (key == null) {
			throw new InternalException("Key cannot be null");
		}
		if (key.isBlank()) {
			throw new InternalException("Key cannot be blank");
		}
		DocumentSnapshot snapshot;
		try {
			snapshot = document.get().get();
		} catch (ExecutionException exception) {
			throw new FirestoreExecutionException(exception);
		} catch (InterruptedException exception) {
			throw new FirestoreInterruptedException(exception);
		}
		return snapshot.get(key);
	}

	@Override
	public final void put(String key, Object value) throws DatabaseException {
		if (key == null) {
			throw new InternalException("Key cannot be null");
		}
		if (key.isBlank()) {
			throw new InternalException("Key cannot be blank");
		}
		document.update(key, value);
	}

	@Override
	public final void del(String key) throws DatabaseException {
		if (key == null) {
			throw new InternalException("Key cannot be null");
		}
		if (key.isBlank()) {
			throw new InternalException("Key cannot be blank");
		}
		document.update(key, FieldValue.delete());
	}
}

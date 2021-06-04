package br.edu.insper.desagil.backend;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;

import org.eclipse.jetty.server.Server;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import br.edu.insper.desagil.backend.core.Handler;

public final class Backend {
	private static final int PORT = 8080;

	public static final void init(String fileName) throws IOException {
		FileInputStream stream = new FileInputStream(fileName);
		FirebaseOptions options = FirebaseOptions.builder()
			.setCredentials(GoogleCredentials.fromStream(stream))
			.build();
		FirebaseApp.initializeApp(options);
	}

	public static final void main(String[] args) throws Exception {
		init("firestore.json");

		Handler handler = new Handler();

		Server server = new Server(PORT);
		server.setHandler(handler);
		server.start();

		String address = InetAddress.getLocalHost().getHostAddress();
		System.out.println("Waiting on http://" + address + ':' + PORT);
	}
}

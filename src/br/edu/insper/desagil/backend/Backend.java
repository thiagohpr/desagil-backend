package br.edu.insper.desagil.backend;

import java.io.FileInputStream;
import java.net.InetAddress;

import org.eclipse.jetty.server.Server;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import br.edu.insper.desagil.backend.core.Dispatcher;

public class Backend {
	public static void main(String[] args) throws Exception {
		InetAddress address = InetAddress.getLocalHost();
		System.out.println(address.getHostAddress());

		FileInputStream stream = new FileInputStream("firestore.json");
		FirebaseOptions options = FirebaseOptions.builder()
			.setCredentials(GoogleCredentials.fromStream(stream))
			.build();
		FirebaseApp.initializeApp(options);

		Dispatcher dispatcher = new Dispatcher();

		Server server = new Server(8080);
		server.setHandler(dispatcher);
		server.start();
	}
}

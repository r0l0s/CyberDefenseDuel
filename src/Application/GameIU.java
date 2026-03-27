package Application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GameIU extends Application {

    // Estado general de la interfaz.
    private Stage ventana;
    private String usuario = "";
    private String avatarElegido = "Captain Firewall";
    private String mapaElegido = "Data Center Dojo";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Guardamos el stage (ventana) una vez y lo vamos reciclando entre pantallas.
        ventana = stage;
        ventana.setTitle("Cyber Defense Duel - JavaFX UI");
        //ventana.setMaximized(true);

        // Se inicia el proceso de login.
        mostrarPantallaLogin();
        ventana.show();
    }

    // 1) Pantalla de login/registro.
    private void mostrarPantallaLogin() {
        // Root base de esta pantalla (el Root es el pane principal).
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0a1022, #0f1a36, #111f47);");
        root.setPadding(new Insets(28));

        Label titulo = new Label("CYBER DEFENSE DUEL");
        titulo.setTextFill(Color.web("#f8fafc"));
        titulo.setFont(Font.font("Segoe UI", 36));

        Label subtitulo = new Label("1 vs 1 Virus Battle Game");
        subtitulo.setTextFill(Color.web("#bfdbfe"));
        subtitulo.setFont(Font.font("Segoe UI", 16));

        VBox cajaSuperior = new VBox(8, titulo, subtitulo);
        cajaSuperior.setAlignment(Pos.CENTER);
        root.setTop(cajaSuperior);

        // Inputs de acceso.
        TextField campoUsuario = new TextField();
        campoUsuario.setPromptText("Username");
        campoUsuario.setPrefHeight(40);

        PasswordField campoContrasena = new PasswordField();
        campoContrasena.setPromptText("Password");
        campoContrasena.setPrefHeight(40);

        Label mensaje = new Label("Enter your credentials to continue.");
        mensaje.setTextFill(Color.web("#dbeafe"));
        mensaje.setFont(Font.font("Segoe UI", 14));

        Button botonLogin = new Button("Login");
        botonLogin.setPrefHeight(38);
        botonLogin.setStyle("-fx-background-color: #0ea5e9; -fx-text-fill: white; -fx-background-radius: 8;");

        Button botonRegistro = new Button("Register");
        botonRegistro.setPrefHeight(38);
        botonRegistro.setStyle("-fx-background-color: #334155; -fx-text-fill: #e2e8f0; -fx-background-radius: 8;");

        HBox filaBotones = new HBox(12, botonLogin, botonRegistro);
        filaBotones.setAlignment(Pos.CENTER);

        VBox formulario = new VBox(14, campoUsuario, campoContrasena, filaBotones, mensaje);
        formulario.setAlignment(Pos.CENTER);
        formulario.setPadding(new Insets(24));

        StackPane tarjeta = new StackPane(formulario);
        tarjeta.setPadding(new Insets(28));
        tarjeta.setStyle("-fx-background-color: rgba(255, 255, 255, 0.08);"
                + "-fx-border-color: #7dd3fc; -fx-border-radius: 12; -fx-background-radius: 12;");
        tarjeta.setMaxWidth(500);

        StackPane cajaCentro = new StackPane(tarjeta);
        cajaCentro.setPadding(new Insets(20, 120, 20, 120));
        root.setCenter(cajaCentro);

        // Si todo esta bien, pasamos a seleccionar avatar (si se escribio algo).
        botonLogin.setOnAction(e -> {
            if (estaVacio(campoUsuario.getText()) || estaVacio(campoContrasena.getText())) {
                mensaje.setText("Completa usuario y contrasena.");
                mensaje.setTextFill(Color.web("#fecaca"));
                return;
            }
            usuario = campoUsuario.getText().trim();
            mostrarPantallaAvatar();
        });

        // Simula registro rapido y continua igual que login.
        botonRegistro.setOnAction(e -> {
            if (estaVacio(campoUsuario.getText()) || estaVacio(campoContrasena.getText())) {
                mensaje.setText("Completa usuario y contrasena para registrarte.");
                mensaje.setTextFill(Color.web("#fecaca"));
                return;
            }
            usuario = campoUsuario.getText().trim();
            mensaje.setText("Registro exitoso.");
            mensaje.setTextFill(Color.web("#86efac"));
            mostrarPantallaAvatar();
        });

        Scene escena = new Scene(root, 1200, 760);
        //Scene escena = new Scene(root);
        ventana.setScene(escena);
    }

    // 2) Pantalla de seleccion del avatar.
    private void mostrarPantallaAvatar() {
        // Mismo estilo visual para mantener consistencia y se vea todo parejo.
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0a1022, #0f1a36, #111f47);");
        root.setPadding(new Insets(24));

        Label titulo = new Label("Select Your Avatar");
        titulo.setFont(Font.font("Segoe UI", 32));
        titulo.setTextFill(Color.web("#f8fafc"));

        Label ayuda = new Label("Choose your cyber hero before entering the duel.");
        ayuda.setFont(Font.font("Segoe UI", 15));
        ayuda.setTextFill(Color.web("#bfdbfe"));

        VBox arriba = new VBox(8, titulo, ayuda);
        arriba.setAlignment(Pos.CENTER_LEFT);
        root.setTop(arriba);

        StackPane avatar1 = crearTarjetaAvatar("Captain Firewall", "Shield specialist", "#38bdf8");
        StackPane avatar2 = crearTarjetaAvatar("Byte Ninja", "Stealth defender", "#f59e0b");
        StackPane avatar3 = crearTarjetaAvatar("Malware Muncher", "Threat cleaner", "#34d399");
        StackPane avatar4 = crearTarjetaAvatar("Crypto Llama", "Encryption master", "#f472b6");
        StackPane avatar5 = crearTarjetaAvatar("Packet Pirate", "Network raider", "#a78bfa");

        HBox fila1 = new HBox(16, avatar1, avatar2, avatar3);
        fila1.setAlignment(Pos.CENTER);
        HBox fila2 = new HBox(16, avatar4, avatar5);
        fila2.setAlignment(Pos.CENTER);
        VBox centro = new VBox(16, fila1, fila2);
        centro.setAlignment(Pos.CENTER);
        root.setCenter(centro);

        // Etiqueta que muestra lo que el usuario va eligiendo.
        Label seleccionado = new Label("Selected: " + avatarElegido);
        seleccionado.setTextFill(Color.web("#dbeafe"));
        seleccionado.setFont(Font.font("Segoe UI", 15));

        Button atras = new Button("Back");
        atras.setPrefHeight(38);
        atras.setStyle("-fx-background-color: #334155; -fx-text-fill: #e2e8f0; -fx-background-radius: 8;");

        Button continuar = new Button("Continue to Map Selection");
        continuar.setPrefHeight(38);
        continuar.setStyle("-fx-background-color: #0ea5e9; -fx-text-fill: white; -fx-background-radius: 8;");

        HBox acciones = new HBox(14, atras, continuar);
        acciones.setAlignment(Pos.CENTER_RIGHT);

        BorderPane abajo = new BorderPane();
        abajo.setLeft(seleccionado);
        abajo.setRight(acciones);
        abajo.setPadding(new Insets(18, 0, 0, 0));
        root.setBottom(abajo);

        // Cada click cambia avatar y resalta solo una tarjeta.
        avatar1.setOnMouseClicked(e -> {
            avatarElegido = "Captain Firewall";
            seleccionado.setText("Selected: " + avatarElegido);
            avatar1.setStyle(estiloTarjetaSeleccionada());
            avatar2.setStyle(estiloTarjetaNormal());
            avatar3.setStyle(estiloTarjetaNormal());
            avatar4.setStyle(estiloTarjetaNormal());
            avatar5.setStyle(estiloTarjetaNormal());
        });

        avatar2.setOnMouseClicked(e -> {
            avatarElegido = "Byte Ninja";
            seleccionado.setText("Selected: " + avatarElegido);
            avatar1.setStyle(estiloTarjetaNormal());
            avatar2.setStyle(estiloTarjetaSeleccionada());
            avatar3.setStyle(estiloTarjetaNormal());
            avatar4.setStyle(estiloTarjetaNormal());
            avatar5.setStyle(estiloTarjetaNormal());
        });

        avatar3.setOnMouseClicked(e -> {
            avatarElegido = "Malware Muncher";
            seleccionado.setText("Selected: " + avatarElegido);
            avatar1.setStyle(estiloTarjetaNormal());
            avatar2.setStyle(estiloTarjetaNormal());
            avatar3.setStyle(estiloTarjetaSeleccionada());
            avatar4.setStyle(estiloTarjetaNormal());
            avatar5.setStyle(estiloTarjetaNormal());
        });

        avatar4.setOnMouseClicked(e -> {
            avatarElegido = "Crypto Llama";
            seleccionado.setText("Selected: " + avatarElegido);
            avatar1.setStyle(estiloTarjetaNormal());
            avatar2.setStyle(estiloTarjetaNormal());
            avatar3.setStyle(estiloTarjetaNormal());
            avatar4.setStyle(estiloTarjetaSeleccionada());
            avatar5.setStyle(estiloTarjetaNormal());
        });

        avatar5.setOnMouseClicked(e -> {
            avatarElegido = "Packet Pirate";
            seleccionado.setText("Selected: " + avatarElegido);
            avatar1.setStyle(estiloTarjetaNormal());
            avatar2.setStyle(estiloTarjetaNormal());
            avatar3.setStyle(estiloTarjetaNormal());
            avatar4.setStyle(estiloTarjetaNormal());
            avatar5.setStyle(estiloTarjetaSeleccionada());
        });

        atras.setOnAction(e -> mostrarPantallaLogin());
        // Seguimos al mapa cuando ya hay avatar.
        continuar.setOnAction(e -> mostrarPantallaMapa());

        Scene escena = new Scene(root, 1200, 760);
        ventana.setScene(escena);
    }

    // 3) Pantalla de seleccion de mapa.
    private void mostrarPantallaMapa() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #0a1022, #0f1a36, #111f47);");
        root.setPadding(new Insets(24));

        Label titulo = new Label("Choose Battle Map");
        titulo.setTextFill(Color.web("#f8fafc"));
        titulo.setFont(Font.font("Segoe UI", 32));

        Label ayuda = new Label("Each arena has a different visual style.");
        ayuda.setTextFill(Color.web("#bfdbfe"));
        ayuda.setFont(Font.font("Segoe UI", 15));

        VBox arriba = new VBox(8, titulo, ayuda);
        root.setTop(arriba);

        StackPane dojoCard = crearTarjetaMapa(
                "Data Center Dojo",
                "Map description: cuando ya se sepa cómo va a ser el mapa, se pone la descripción acá",
                "linear-gradient(to bottom right, #0ea5e9, #1d4ed8)");

        StackPane carnivalCard = crearTarjetaMapa(
                "Packet Bay Carnival",
                "Map description: cuando ya se sepa cómo va a ser el mapa, se pone la descripción acá",
                "linear-gradient(to bottom right, #f97316, #db2777)");

        HBox filaMapas = new HBox(24, dojoCard, carnivalCard);
        filaMapas.setAlignment(Pos.CENTER);
        root.setCenter(filaMapas);

        // Estado visible de la seleccion actual de mapa.
        Label seleccionado = new Label("Selected map: " + mapaElegido);
        seleccionado.setTextFill(Color.web("#dbeafe"));
        seleccionado.setFont(Font.font("Segoe UI", 15));

        dojoCard.setOnMouseClicked(e -> {
            mapaElegido = "Data Center Dojo";
            seleccionado.setText("Selected map: " + mapaElegido);
            dojoCard.setStyle(estiloTarjetaSeleccionada() + "-fx-background-color: linear-gradient(to bottom right, #0ea5e9, #1d4ed8);");
            carnivalCard.setStyle(estiloTarjetaNormal() + "-fx-background-color: linear-gradient(to bottom right, #f97316, #db2777);");
        });

        carnivalCard.setOnMouseClicked(e -> {
            mapaElegido = "Packet Bay Carnival";
            seleccionado.setText("Selected map: " + mapaElegido);
            carnivalCard.setStyle(estiloTarjetaSeleccionada() + "-fx-background-color: linear-gradient(to bottom right, #f97316, #db2777);");
            dojoCard.setStyle(estiloTarjetaNormal() + "-fx-background-color: linear-gradient(to bottom right, #0ea5e9, #1d4ed8);");
        });

        Button atras = new Button("Back");
        atras.setPrefHeight(38);
        atras.setStyle("-fx-background-color: #334155; -fx-text-fill: #e2e8f0; -fx-background-radius: 8;");

        Button iniciar = new Button("Confirm Map");
        iniciar.setPrefHeight(38);
        iniciar.setStyle("-fx-background-color: #0ea5e9; -fx-text-fill: white; -fx-background-radius: 8;");

        atras.setOnAction(e -> mostrarPantallaAvatar());
        // Solo confirma visualmente, no lanza gameplay todavia.
        iniciar.setOnAction(e -> seleccionado.setText("Selected map: " + mapaElegido + " (ready)"));

        HBox acciones = new HBox(14, atras, iniciar);
        acciones.setAlignment(Pos.CENTER_RIGHT);

        BorderPane abajo = new BorderPane();
        abajo.setLeft(seleccionado);
        abajo.setRight(acciones);
        abajo.setPadding(new Insets(18, 0, 0, 0));

        root.setBottom(abajo);


        Scene escena = new Scene(root, 1200, 760);
        ventana.setScene(escena);
    }

    // Tarjeta para cada avatar.
    private StackPane crearTarjetaAvatar(String nombre, String rol, String color) {
        // Mini bloque reutilizable para no duplicar UI de avatares.
        Label icon = new Label("●");
        icon.setTextFill(Color.web(color));
        icon.setFont(Font.font("Segoe UI", 34));

        Label title = new Label(nombre);
        title.setTextFill(Color.web("#f8fafc"));
        title.setFont(Font.font("Segoe UI", 18));

        Label subtitle = new Label(rol);
        subtitle.setTextFill(Color.web("#bfdbfe"));
        subtitle.setFont(Font.font("Segoe UI", 13));

        VBox content = new VBox(8, icon, title, subtitle);
        content.setAlignment(Pos.CENTER);

        StackPane card = new StackPane(content);
        if (nombre.equals(avatarElegido)) {
            card.setStyle(estiloTarjetaSeleccionada());
        } else {
            card.setStyle(estiloTarjetaNormal());
        }
        card.setPadding(new Insets(16));
        card.setPrefSize(260, 170);
        return card;
    }

    // Tarjetas para los mapas.
    private StackPane crearTarjetaMapa(String mapName, String description, String gradientBg) {
        // Pintar cada opcion de mapa.
        Label title = new Label(mapName);
        title.setTextFill(Color.web("#ffffff"));
        title.setFont(Font.font("Segoe UI", 22));

        Label body = new Label(description);
        body.setWrapText(true);
        body.setMaxWidth(280);
        body.setTextFill(Color.web("#e2e8f0"));
        body.setFont(Font.font("Segoe UI", 14));

        VBox content = new VBox(10, title, body);
        content.setAlignment(Pos.CENTER_LEFT);

        StackPane card = new StackPane(content);
        card.setPadding(new Insets(20));
        card.setPrefSize(360, 260);
        card.setStyle(estiloTarjetaNormal() + "-fx-background-color: " + gradientBg + ";");
        return card;
    }

    private String estiloTarjetaNormal() {
        return "-fx-background-color: rgba(255, 255, 255, 0.08);"
                + "-fx-border-color: #7dd3fc; -fx-border-radius: 12; -fx-background-radius: 12;";
    }

    private String estiloTarjetaSeleccionada() {
        return "-fx-background-color: rgba(34, 197, 94, 0.20);"
                + "-fx-border-color: #22c55e; -fx-border-width: 2; -fx-border-radius: 12; -fx-background-radius: 12;";
    }

    private boolean estaVacio(String value) {
        // Validar campos de texto.
        return value == null || value.trim().isEmpty();
    }
}

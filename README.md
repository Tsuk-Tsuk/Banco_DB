<img width="829" height="454" alt="image" src="https://github.com/user-attachments/assets/8ae676fa-c8db-45f6-b53c-800c4ce60efe" /># Simulador Bancario en Java Swing

## Funcionalidades
- Inicio de sesión con validación en base de datos.
<img width="373" height="111" alt="image" src="https://github.com/user-attachments/assets/5cedc40f-797c-46d9-a4ab-7e0558fa2fe0" />
<img width="283" height="188" alt="image" src="https://github.com/user-attachments/assets/1f235ab7-12d9-4fe8-beb3-e5b138cac15a" />
<img width="285" height="189" alt="image" src="https://github.com/user-attachments/assets/3a17e40b-3d81-4cca-abe2-57a89f711550" />
<img width="385" height="290" alt="image" src="https://github.com/user-attachments/assets/158bea9a-49ab-4ab9-ade1-5a816dc59a49" />


- Operaciones: depósito, retiro y transferencia.
- Historial de transacciones.
<img width="383" height="291" alt="image" src="https://github.com/user-attachments/assets/b7581a43-63ca-44f6-86a2-c5769b0ff8c4" />

- Saldo actualizado en pantalla.
<img width="383" height="291" alt="image" src="https://github.com/user-attachments/assets/d5f9dda4-d9aa-42ae-a0df-9f19392b6152" />

## Organización
- `config/DBConnection.java`: conexión centralizada a la BD.
- `forms/LoginForm.java`: formulario de login.
- `forms/BancoForm.java`: operaciones bancarias.
- `model/Cliente.java`: clase modelo para cliente.
<img width="423" height="221" alt="image" src="https://github.com/user-attachments/assets/31acea6e-7da3-46bc-ac95-09a198eb6d37" />

## Ejecución
1. Configurar base de datos MySQL.
2. Ejecutar `Main.java`.
   <img width="829" height="454" alt="image" src="https://github.com/user-attachments/assets/2d905dba-21d5-4e4c-a3ee-1e6bbc603056" />
3. Ingresar credenciales válidas.
   

### 3. Extras de Diseño
- Uso de `GroupLayout` o `GridBagLayout` para una interfaz clara.
- Estilo profesional: fuentes legibles, espacios adecuados y centrado visual.

---

### Explicacion del codigo

---

## LoginForm

### Función principal
El **LoginForm** es la pantalla de inicio de sesión del sistema.  
Su objetivo es validar las credenciales del usuario contra la base de datos y controlar los intentos fallidos de acceso.

### Datos que procesa
- **Usuario**: ingresado en un `JTextField`.
- **Contraseña**: ingresada en un `JPasswordField`.
- **Intentos fallidos**: contador interno que bloquea el acceso tras 3 errores consecutivos.

### Conexión con la lógica del sistema
- Se conecta a la base de datos mediante la clase `DBConnection`.
- Ejecuta la consulta SQL:
  ```sql
  SELECT nombre, saldo FROM clientes WHERE usuario=? AND password=? AND activo=1;
---

## BancoForm

### Función principal
El **BancoForm** es la ventana principal de operaciones bancarias del sistema.  
Permite al cliente autenticado visualizar su saldo y realizar transacciones como depósitos, retiros y transferencias.

### Datos que procesa
- **Saldo del cliente**: valor numérico que se actualiza tras cada operación.
- **Monto ingresado**: cantidad introducida por el usuario en los campos de texto.
- **Destinatario de transferencia**: nombre del cliente receptor en caso de transferencias.
- **Historial de transacciones**: registro textual mostrado en un `JTextArea`.

### Conexión con la lógica del sistema
- Recibe un objeto `Cliente` desde el **LoginForm** tras la autenticación.
- Cada operación modifica el atributo `saldo` del objeto `Cliente`.
- Después de cada transacción:
  - Se actualiza el `JLabel` que muestra el saldo.
  - Se añade un registro descriptivo al historial (`JTextArea`).
- El botón **Salir** cierra la aplicación.

### Flujo funcional
1. El cliente inicia sesión y se abre el **BancoForm** con sus datos.
2. El formulario muestra el **nombre del cliente** y su **saldo actual**.
3. El cliente puede:
   - **Depositar**: sumar el monto al saldo.
   - **Retirar**: restar el monto si hay saldo suficiente.
   - **Transferir**: restar el monto y registrar el destinatario.
4. Cada operación se refleja en el historial de transacciones.
5. El saldo se actualiza en tiempo real en la interfaz.

### Buenas prácticas
- Validar que el monto ingresado sea numérico y positivo.
- Evitar retiros o transferencias si el saldo es insuficiente.
- Mostrar mensajes claros al usuario en cada operación.
- Mantener el historial visible para transparencia de las transacciones.

### Pruebas recomendadas
- Realizar un depósito y comprobar que el saldo aumenta correctamente.
- Realizar un retiro válido y verificar que el saldo disminuye.
- Intentar un retiro mayor al saldo y comprobar que se muestra un error.
- Realizar una transferencia y verificar que se registra en el historial.
- Probar el botón **Salir** y confirmar que la aplicación se cierra.
---

## DBConnection (Configuración)

### Función principal
La clase **DBConnection** centraliza la conexión a la base de datos del sistema.  
Su objetivo es proveer un único punto de acceso para ejecutar consultas SQL, evitando repetir credenciales y configuraciones en cada formulario.

### Datos que procesa
- **URL de conexión**: dirección JDBC hacia la base de datos (ejemplo: `jdbc:mysql://localhost:3306/banco`).
- **Usuario y contraseña de la BD**: credenciales para autenticar la conexión.
- **Objeto `Connection`**: instancia reutilizable que permite ejecutar consultas y transacciones.

### Conexión con la lógica del sistema
- Es invocada en el **LoginForm** para validar credenciales de usuario.
- Puede ser utilizada en el **BancoForm** si se decide persistir las operaciones (depósitos, retiros, transferencias) en la base de datos.
- Garantiza que toda la aplicación use un único punto de acceso a la base de datos, facilitando el mantenimiento y la seguridad.

### Ejemplo de implementación
```java
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/banco";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
````

---
## Cliente (Modelo)

### Función principal
La clase **Cliente** representa al usuario autenticado dentro del sistema bancario.  
Es el modelo que encapsula los datos básicos del cliente y su saldo, permitiendo que las operaciones bancarias se realicen sobre este objeto.

### Datos que procesa
- **Usuario**: identificador único del cliente en el sistema.
- **Nombre**: nombre completo del cliente.
- **Saldo**: valor numérico que refleja el dinero disponible en la cuenta.

### Conexión con la lógica del sistema
- El objeto `Cliente` se crea en el **LoginForm** después de validar las credenciales contra la base de datos.
- Este objeto se pasa como parámetro al **BancoForm**, donde se realizan las operaciones bancarias.
- Cada transacción (depósito, retiro, transferencia) modifica el atributo `saldo` del objeto `Cliente`.
- El saldo actualizado se refleja en la interfaz gráfica y en el historial de transacciones.

### Ejemplo de implementación
```java
package model;

public class Cliente {
    private String usuario;
    private String nombre;
    private double saldo;

    public Cliente(String usuario, String nombre, double saldo) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.saldo = saldo;
    }

    public String getUsuario() { return usuario; }
    public String getNombre() { return nombre; }
    public double getSaldo() { return saldo; }

    public void setSaldo(double saldo) { this.saldo = saldo; }
}
````
---

## Main (Punto de entrada)

### Función principal
La clase **Main** es el punto de inicio de la aplicación.  
Su objetivo es inicializar la interfaz gráfica y mostrar el formulario de **Login** al usuario.

### Datos que procesa
- No procesa datos directamente, solo **inicializa la aplicación**.
- Crea la primera ventana (`LoginForm`) que gestiona las credenciales del usuario.

### Conexión con la lógica del sistema
- Llama al formulario **LoginForm**.
- Una vez que el usuario se autentica, el flujo pasa al **BancoForm**.
- No contiene lógica de negocio ni conexión a la base de datos; únicamente arranca la aplicación.

### Ejemplo de implementación
```java
package src;

import forms.Login;

public class Main {
    public static void main(String[] args) {
        // Inicializa la aplicación mostrando el formulario de login
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
}
````

---
## Base de Datos

### Script SQL (MySQL)
```sql
CREATE DATABASE banco;
USE banco;

CREATE TABLE clientes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    saldo DECIMAL(10,2) DEFAULT 1000.00,
    activo BOOLEAN DEFAULT TRUE
);

INSERT INTO clientes (usuario, password, nombre, saldo, activo)
VALUES 
('jdoe', '1234', 'John Doe', 1000.00, TRUE),
('maria', 'abcd', 'Maria Lopez', 1500.00, TRUE),
('carlos', 'pass', 'Carlos Perez', 500.00, FALSE);
````

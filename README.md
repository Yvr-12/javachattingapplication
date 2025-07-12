# Java Chat Application

## Overview
A secure, real-time chat application using Java Sockets and Swing GUI. Features AES encryption for messages and persistent chat history for both client and server.

## Features
- Real-time messaging (client-server)
- GUI using Java Swing
- AES-encrypted messages
- Chat history saved and loaded on both sides
- Usernames for sender/receiver

## How to Run

1. **Compile:**
   ```sh
   cd chatapp/src
   javac chatapp/*.java
   ```
2. **Run Server:**
   ```sh
   java chatapp.server
   ```
3. **Run Client:**
   ```sh
   java chatapp.client
   ```

## Project Structure
- `chatapp/src/chatapp/` : Java source files
- `icons/` : Images for GUI (optional)
- `chat_history_client.txt` : Client chat history
- `chat_history_server.txt` : Server chat history
- `Dockerfile` : For server deployment (optional)

## Technologies Used
- Java SE (Swing, Sockets, File I/O)
- AES Encryption (Java Cryptography)

## Authors
- Your Name
- SRM Institute of Science and Technology

## Future Enhancements
- Multi-client support
- User authentication
- Group chat
- File sharing
- Database integration for chat history

## Screenshots
(Add screenshots of your GUI here)

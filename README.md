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
- Yashvardhan Rathi

## Future Enhancements
- Multi-client support
- User authentication
- Group chat
- File sharing
- Database integration for chat history

## Screenshots
<img width="1411" height="868" alt="Screenshot 2025-06-18 132449" src="https://github.com/user-attachments/assets/2569a907-602d-4fe8-9d20-bcbe6d91183c" />
<img width="1380" height="889" alt="Screenshot 2025-06-18 132714" src="https://github.com/user-attachments/assets/4a22ead4-3b28-477c-b755-48d549000a49" />
<img width="1387" height="810" alt="Screenshot 2025-06-18 132812" src="https://github.com/user-attachments/assets/73ef327b-73fc-43fb-887b-3458957d0fb9" />
<img width="450" height="312" alt="Screenshot 2025-07-12 155506" src="https://github.com/user-attachments/assets/f85d16e5-bfe7-4787-b391-f0088500bf4d" />
<img width="1359" height="937" alt="Screenshot 2025-07-12 155756" src="https://github.com/user-attachments/assets/3858f618-c366-499a-b268-d329f54cfa18" />
<img width="1387" height="932" alt="Screenshot 2025-07-12 155728" src="https://github.com/user-attachments/assets/b6a046f5-8c5e-4b5f-a1b9-e03fa6e91827" />

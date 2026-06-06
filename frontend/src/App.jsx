import { useState, useEffect, useRef } from "react";
import axios from "axios";
import "./App.css";

const generateConversationId = () => {
  return crypto.randomUUID();
};

function App() {

  const [message, setMessage] = useState("");
  const [loading, setLoading] = useState(false);
  const [chats, setChats] = useState([]);

  const [conversationId, setConversationId] = useState(() => {

    const savedId = localStorage.getItem("conversationId");

    if (savedId) {
      return savedId;
    }

    const newId = generateConversationId();
    localStorage.setItem("conversationId", newId);

    return newId;
  });

  const chatEndRef = useRef(null);

  useEffect(() => {
    if (conversationId) {
      fetchConversation();
    }

  }, [conversationId]);

  const fetchConversation = async () => {

    try {

      const response = await axios.get(
        `http://localhost:8080/api/chat/history/${conversationId}`
      );

      const history = [];

      response.data.forEach((chat) => {

        history.push({
          type: "user",
          text: chat.userMessage
        });

        history.push({
          type: "bot",
          text: chat.botReply
        });

      });

      setChats(history);

    } catch (error) {

      console.log(error);

    }

  };

  useEffect(() => {
    chatEndRef.current?.scrollIntoView({
      behavior: "smooth"
    });
  }, [chats]);

  const sendMessage = async () => {

    if (!message.trim()) return;

    const userMessage = message;

    setChats((prev) => [
      ...prev,
      { type: "user", text: userMessage }
    ]);

    setMessage("");

    try {

      setLoading(true);

      const response = await axios.post(
        "http://localhost:8080/api/chat",
        {
          message: userMessage,
          conversationId: conversationId
        }
      );

      setChats((prev) => [
        ...prev,
        {
          type: "bot",
          text: response.data.reply
        }
      ]);

    } catch (error) {

      setChats((prev) => [
        ...prev,
        {
          type: "bot",
          text: "Server Error"
        }
      ]);

    } finally {
      setLoading(false);
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === "Enter") {
      sendMessage();
    }
  };

  const startNewChat = () => {

    const newConversationId =
      generateConversationId();

    localStorage.setItem(
      "conversationId",
      newConversationId
    );

    setConversationId(newConversationId);

    setChats([]);

  };

  return (
    <div className="app">

      <div className="chat-container">

        <div className="header">

        <h1>
          AI Chatbot 🤖
        </h1>

        <button
          className="new-chat-btn"
          onClick={startNewChat}
        >
          + New Chat
        </button>

      </div>

        <p className="subtitle">
          Java + Spring Boot + React
        </p>

        <div className="chat-box">

          {
            chats.map((chat, index) => (

              <div
                key={index}
                className={
                  chat.type === "user"
                    ? "user-message"
                    : "bot-message"
                }
              >
                {chat.text}
              </div>

            ))
          }

          {
            loading &&
            <div className="bot-message">
              Typing...
            </div>
          }

          <div ref={chatEndRef}></div>

        </div>

        <div className="input-area">

          <input
            type="text"
            placeholder="Type your message..."
            value={message}
            onChange={(e) => setMessage(e.target.value)}
            onKeyDown={handleKeyDown}
          />

          <button onClick={sendMessage}>
            Send
          </button>

        </div>

      </div>

    </div>
  );
}

export default App;
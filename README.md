DevLegacy AI 🧠💻
The AI-powered journal for developers. Turn your code snippets into a living, intelligent knowledge base.

(Note: You should replace the link above with a real screenshot of your application's dashboard!)

What it Does
DevLegacy AI is a full-stack web application designed to help developers learn from their own code. Users can paste in code snippets, and the app leverages the Google Gemini API to provide an instant, in-depth analysis.

✨ Features
AI-Powered Analysis: Get a plain English explanation of what your code does, suggestions for improvement, and best practices.

Automatic Unit Tests: Instantly generate JUnit tests for your Java code to ensure quality and robustness.

Personal Code History: Every analysis is saved to a personal, searchable history, creating an intelligent code diary that grows with you.

Secure Authentication: User accounts and data are kept safe and private with Auth0.

🚀 Tech Stack
This project was built with a modern, robust tech stack, integrating multiple powerful services.

Category

Technology

Frontend

React, TypeScript, Vite, Tailwind CSS, Shadcn/ui

Backend

Java, Spring Boot, Maven

AI Core

Google Gemini API

Database

MongoDB Atlas

Authentication

Auth0

Deployment

Frontend on Netlify, Backend on Railway

🏁 Getting Started
To run this project locally, you will need two terminals.

Prerequisites
Java 17 or later

Node.js and npm

A MongoDB Atlas account

A Google Gemini API Key

An Auth0 account

Backend Setup
Navigate to the backend directory:

cd backend

Configure your secrets:

Open src/main/resources/application.properties.

Add your MongoDB connection string and Gemini API key:

spring.data.mongodb.uri=YOUR_MONGODB_CONNECTION_STRING
gemini.api.key=YOUR_GEMINI_API_KEY

Run the application:

Open the project in your favorite Java IDE (like IntelliJ or STS).

Run the DevlegacyApplication.java file.

The server will start on http://localhost:8080.

Frontend Setup
Navigate to the frontend directory:

cd frontend

Install dependencies:

npm install --legacy-peer-deps

Configure your secrets:

Open src/main.tsx.

Replace the placeholder values for domain, clientId, and audience with your actual Auth0 credentials.

Run the application:

npm run dev

The frontend will be available at http://localhost:5173.

This project was built for the AI Hackfest hackathon.

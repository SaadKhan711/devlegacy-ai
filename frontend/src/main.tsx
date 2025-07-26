import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './app/app';
import './globals.css'; // Or './globals.css' depending on your file name
import { Auth0Provider } from '@auth0/auth0-react';
import { BrowserRouter } from "react-router-dom";

// **REPLACE WITH YOUR REAL DOMAIN AND CLIENT ID**
const domain = 'dev-h5dpnpgh5uu708zs.us.auth0.com';
const clientId = 'SuXfuVg4h2YV8k7PR1syS54Z1OH1foLA';

// **PASTE THE API IDENTIFIER YOU CREATED IN THE AUTH0 DASHBOARD**
const audience = 'https://api.devlegacy.ai';

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <BrowserRouter>
      <Auth0Provider
        domain={domain}
        clientId={clientId}
        authorizationParams={{
          redirect_uri: window.location.origin + '/dashboard',
          audience: audience,
        }}
      >
        <App />
      </Auth0Provider>
    </BrowserRouter>
  </React.StrictMode>,
);
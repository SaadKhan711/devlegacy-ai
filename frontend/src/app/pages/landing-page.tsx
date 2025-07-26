import React from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { Button } from "@/components/ui/button";
import { Bot, Book, BrainCircuit } from "lucide-react";

export const LandingPage = () => {
  const { loginWithRedirect } = useAuth0();

  return (
    <div className="w-full min-h-screen bg-slate-900 text-white">
      <div className="container mx-auto flex flex-col items-center justify-center min-h-screen p-4">
        <div className="text-center mb-12">
          <h1 className="text-5xl md:text-7xl font-bold bg-clip-text text-transparent bg-gradient-to-b from-neutral-50 to-neutral-400 pb-4">
            Unlock Your Code's Legacy
          </h1>
          <p className="mt-4 text-lg text-slate-300 max-w-2xl">
            Turn your code snippets into a living, intelligent journal. Analyze, document, and learn from your own work with the power of AI.
          </p>
        </div>
        <div className="grid grid-cols-1 md:grid-cols-3 gap-8 mb-16">
          <div className="flex flex-col items-center text-center p-6 bg-slate-800/50 rounded-lg border border-slate-700">
            <Bot size={40} className="text-cyan-400 mb-4" />
            <h3 className="text-xl font-semibold mb-2">Deep AI Analysis</h3>
            <p className="text-slate-400">Get explanations, suggestions, and automated tests for any code you write.</p>
          </div>
          <div className="flex flex-col items-center text-center p-6 bg-slate-800/50 rounded-lg border border-slate-700">
            <Book size={40} className="text-purple-400 mb-4" />
            <h3 className="text-xl font-semibold mb-2">Automatic Documentation</h3>
            <p className="text-slate-400">Build a searchable history of your work, intelligently documented for future you.</p>
          </div>
          <div className="flex flex-col items-center text-center p-6 bg-slate-800/50 rounded-lg border border-slate-700">
            <BrainCircuit size={40} className="text-green-400 mb-4" />
            <h3 className="text-xl font-semibold mb-2">Personalized Insights</h3>
            <p className="text-slate-400">Discover patterns and improve your skills by learning from your own code.</p>
          </div>
        </div>
        <Button 
          onClick={() => loginWithRedirect()}
          size="lg"
          className="bg-white text-slate-900 font-bold hover:bg-slate-200 shadow-lg shadow-white/10 transition-all duration-300 transform hover:scale-105"
        >
          Log In to Begin Your Legacy
        </Button>
      </div>
    </div>
  );
};
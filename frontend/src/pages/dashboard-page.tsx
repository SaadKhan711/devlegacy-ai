import React from "react";
import { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import axios, { AxiosError } from "axios";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { BrainCircuit, LogOut, AlertCircle } from "lucide-react";

type CodeEntry = {
  id: string;
  originalCode: string;
  explanation: string;
  suggestions: string;
  generatedTests: string;
};

export const DashboardPage = () => {
  const [code, setCode] = useState("");
  const [analysis, setAnalysis] = useState<CodeEntry | null>(null);
  const [history, setHistory] = useState<CodeEntry[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { user, logout, getAccessTokenSilently } = useAuth0();

  const fetchHistory = async () => {
    try {
      const token = await getAccessTokenSilently();
      const response = await axios.get("http://localhost:8080/api/code-entries", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setHistory(response.data.reverse());
    } catch (err) {
      console.error("Error fetching history:", err);
    }
  };

  useEffect(() => {
    fetchHistory();
  }, []);

  const handleSubmit = async () => {
    if (!code.trim()) return;
    setIsLoading(true);
    setError(null);
    setAnalysis(null);
    try {
      const token = await getAccessTokenSilently();
      const response = await axios.post(
        "http://localhost:8080/api/code-entries",
        { code },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setAnalysis(response.data);
      fetchHistory();
    } catch (err) {
      console.error("Error fetching analysis:", err);
      if (err instanceof AxiosError && err.response) {
        setError(`Analysis failed: ${err.response.statusText}. Please try again.`);
      } else {
        setError("An unexpected error occurred. Please check the console.");
      }
    } finally {
      setIsLoading(false);
    }
  };

  const handleHistoryClick = (entry: CodeEntry) => {
    setCode(entry.originalCode);
    setAnalysis(entry);
    setError(null);
  };

  return (
    <div className="flex flex-col h-screen bg-slate-100 font-sans">
      <header className="flex items-center justify-between p-4 bg-white border-b shadow-sm">
        <div className="flex items-center gap-3">
          <BrainCircuit className="text-slate-800" size={28} />
          <h1 className="text-xl font-bold text-slate-800">DevLegacy AI</h1>
        </div>
        <div className="flex items-center gap-4">
          <p className="text-sm text-slate-600 hidden sm:block">Welcome, {user?.name}</p>
          <Button variant="outline" size="sm" onClick={() => logout({ logoutParams: { returnTo: window.location.origin } })}>
            <LogOut className="mr-2 h-4 w-4" />
            Log Out
          </Button>
        </div>
      </header>

      <main className="flex flex-1 p-4 gap-4 overflow-hidden">
        <Card className="w-1/4 flex flex-col">
          <CardHeader>
            <CardTitle>History</CardTitle>
          </CardHeader>
          <CardContent className="flex-1 overflow-y-auto pr-2">
            {history.length > 0 ? (
              <ul className="space-y-1">
                {history.map((entry) => (
                  <li key={entry.id}>
                    <button
                      className="w-full text-left p-2 rounded-md hover:bg-slate-200 transition-colors text-sm truncate"
                      onClick={() => handleHistoryClick(entry)}
                    >
                      {(entry.originalCode?.split('\n')[0] ?? 'Code Snippet').substring(0, 50)}
                    </button>
                  </li>
                ))}
              </ul>
            ) : (
              <p className="text-sm text-slate-500 px-2">Your past entries will appear here.</p>
            )}
          </CardContent>
        </Card>

        <div className="flex-1 flex flex-col gap-4">
          <Textarea
            className="flex-1 text-sm font-mono rounded-lg shadow-inner p-4 focus:ring-2 focus:ring-slate-400"
            placeholder="Paste your code here..."
            value={code}
            onChange={(e) => setCode(e.target.value)}
          />
          <Button onClick={handleSubmit} disabled={isLoading} size="lg" className="w-full font-bold">
            {isLoading ? (
              <div className="flex items-center justify-center">
                <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-white mr-3"></div>
                Analyzing...
              </div>
            ) : "Analyze Code"}
          </Button>
        </div>

        <Card className="w-1/3 flex flex-col">
          <CardHeader>
            <CardTitle>AI Analysis</CardTitle>
          </CardHeader>
          <CardContent className="flex-1 overflow-y-auto space-y-4">
            {isLoading && <p className="text-center text-slate-500">Generating insights...</p>}
            {error && (
              <div className="bg-red-100 border-l-4 border-red-500 text-red-700 p-4 rounded-md" role="alert">
                <div className="flex">
                  <AlertCircle className="h-5 w-5 text-red-500 mr-3" />
                  <div>
                    <p className="font-bold">Error</p>
                    <p className="text-sm">{error}</p>
                  </div>
                </div>
              </div>
            )}
            {analysis ? (
              <div className="space-y-6">
                <div>
                  <h3 className="font-bold mb-2 text-md">Explanation</h3>
                  <p className="text-sm bg-white p-3 rounded-md border">{analysis.explanation}</p>
                </div>
                <div>
                  <h3 className="font-bold mb-2 text-md">Suggestions</h3>
                  <p className="text-sm whitespace-pre-wrap bg-white p-3 rounded-md border">{analysis.suggestions}</p>
                </div>
                <div>
                  <h3 className="font-bold mb-2 text-md">Generated Tests</h3>
                  <pre className="text-xs bg-slate-900 text-white p-3 rounded-md overflow-x-auto"><code>{analysis.generatedTests}</code></pre>
                </div>
              </div>
            ) : (
              !isLoading && !error && <p className="text-sm text-slate-500 text-center">Your analysis will appear here.</p>
            )}
          </CardContent>
        </Card>
      </main>
    </div>
  );
};
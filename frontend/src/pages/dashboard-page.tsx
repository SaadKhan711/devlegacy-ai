import React, { useState, useEffect } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import axios, { AxiosError } from "axios";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { BrainCircuit, History, LogOut, AlertCircle, Sparkles } from "lucide-react";

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
  const [inputData, setInputData] = useState(""); // NEW input data
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const { user, logout, getAccessTokenSilently } = useAuth0();

  const fetchHistory = async () => {
    try {
      const token = await getAccessTokenSilently();
      const apiUrl = `http://localhost:8080/api/code-entries`;

      const response = await axios.get(apiUrl, {
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
      const apiUrl = `http://localhost:8080/api/code-entries`;

      const response = await axios.post(
        apiUrl,
        { code, inputData }, // also send inputData if needed
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setAnalysis(response.data);
      fetchHistory();
    } catch (err) {
      console.error("Error fetching analysis:", err);
      if (err instanceof AxiosError && err.response) {
        setError(
          `Analysis failed: ${err.response.statusText}. Please try again.`
        );
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
    <div className="flex flex-col min-h-screen bg-slate-900 text-white font-sans">
      {/* HEADER */}
      <header className="flex items-center justify-between p-4 bg-slate-900/80 backdrop-blur-sm border-b border-slate-700">
        <div className="flex items-center gap-3">
          <BrainCircuit className="text-cyan-400" size={28} />
          <h1 className="text-xl font-bold">DevLegacy AI</h1>
        </div>
        <div className="flex items-center gap-4">
          <p className="text-sm text-slate-400 hidden sm:block">
            Welcome, {user?.name}
          </p>
          <Button
            variant="outline"
            size="sm"
            onClick={() =>
              logout({ logoutParams: { returnTo: window.location.origin } })
            }
            className="bg-transparent border-slate-600 hover:bg-slate-800 hover:text-white"
          >
            <LogOut className="mr-2 h-4 w-4" />
            Log Out
          </Button>
        </div>
      </header>

      {/* MAIN GRID */}
      <main className="flex-1 grid grid-cols-1 lg:grid-cols-12 gap-6 p-6 overflow-hidden">

        {/* HISTORY PANEL (LEFT) */}
        <Card className="lg:col-span-3 flex flex-col bg-slate-800/50 border-slate-700 max-h-[88vh] overflow-y-auto">
          <CardHeader className="flex flex-row items-center gap-3 sticky top-0 z-10 bg-slate-800/70">
            <History className="text-purple-400" />
            <CardTitle className="text-white">History</CardTitle>
          </CardHeader>
          <CardContent className="flex-1 pr-2">
            {history.length > 0 ? (
              <ul className="space-y-1">
                {history.map((entry) => (
                  <li key={entry.id}>
                    <button
                      className="w-full text-left p-2 rounded-md hover:bg-slate-700/50 transition-colors text-sm text-slate-300 truncate"
                      onClick={() => handleHistoryClick(entry)}
                    >
                      {(entry.originalCode?.split("\n")[0] ?? "Code Snippet").substring(0, 50)}
                    </button>
                  </li>
                ))}
              </ul>
            ) : (
              <p className="text-sm text-slate-500 px-2">
                Your past entries will appear here.
              </p>
            )}
          </CardContent>
        </Card>

  {/* CODE INPUT PANEL (CENTER) */}
<div className="lg:col-span-5 flex flex-col overflow-y-auto">
  <Card className="flex flex-col bg-slate-800/50 border-slate-700 h-full">
    <CardHeader className="flex flex-row items-center gap-3 sticky top-0 z-10 bg-slate-800/70">
      <Sparkles className="text-yellow-400" />
      <CardTitle className="text-white">Paste your code for analysis</CardTitle>
    </CardHeader>

    <CardContent className="flex-1 flex flex-col gap-4">
      <Textarea
        className="flex-1 min-h-[300px] text-sm font-mono rounded-lg bg-slate-900 border-slate-700 p-4 focus:ring-2 focus:ring-cyan-400 text-slate-200 shadow-inner resize-none"
        placeholder="Paste your code here..."
        value={code}
        onChange={(e) => setCode(e.target.value)}
      />

      <Button
        onClick={handleSubmit}
        disabled={isLoading}
        size="lg"
        className="w-full font-bold bg-cyan-500 hover:bg-cyan-600 text-slate-900 transition-all duration-300 transform hover:scale-105 shadow-lg shadow-cyan-500/20 disabled:opacity-50 disabled:scale-100"
      >
        {isLoading ? (
          <div className="flex items-center justify-center">
            <div className="animate-spin rounded-full h-5 w-5 border-b-2 border-slate-900 mr-3"></div>
            Analyzing...
          </div>
        ) : (
          <div className="flex items-center justify-center">
            <Sparkles className="mr-2 h-5 w-5" />
            Analyze Code
          </div>
        )}
      </Button>
    </CardContent>
  </Card>
</div>


        {/* AI ANALYSIS PANEL (RIGHT) */}
        <Card className="lg:col-span-4 flex flex-col bg-slate-800/50 border-slate-700 max-h-[88vh] overflow-y-auto">
     <CardHeader className="flex flex-row items-center gap-3 sticky top-0 z-10 bg-slate-800">
  <Sparkles className="text-green-400" />
  <CardTitle className="text-white">AI Analysis</CardTitle>
</CardHeader>

          <CardContent className="flex-1 space-y-4">
            {isLoading && (
              <p className="text-center text-slate-500 animate-pulse">
                Generating insights...
              </p>
            )}
            {error && (
              <div className="bg-red-900/50 border border-red-700 text-red-300 p-4 rounded-md flex items-center" role="alert">
                <AlertCircle className="h-5 w-5 text-red-400 mr-3" />
                <div>
                  <p className="font-bold">Error</p>
                  <p className="text-sm">{error}</p>
                </div>
              </div>
            )}
            {analysis ? (
              <div className="space-y-6">
                <div>
                  <h3 className="font-bold mb-2 text-md text-slate-300">Explanation</h3>
                  <p className="text-sm bg-slate-900/70 p-3 rounded-md border border-slate-700 text-slate-100">
                    {analysis.explanation}
                  </p>
                </div>
                <div>
                  <h3 className="font-bold mb-2 text-md text-slate-300">Suggestions</h3>
                  <p className="text-sm whitespace-pre-wrap bg-slate-900/70 p-3 rounded-md border border-slate-700 text-slate-100">
                    {analysis.suggestions}
                  </p>
                </div>
                <div>
                  <h3 className="font-bold mb-2 text-md text-slate-300">Generated Tests</h3>
                  <pre className="text-xs bg-slate-950 text-emerald-400 p-3 rounded-md overflow-x-auto border border-slate-700">
                    <code>{analysis.generatedTests}</code>
                  </pre>
                </div>
              </div>
            ) : (
              !isLoading && !error && (
                <p className="text-sm text-slate-500 text-center">
                  Your analysis will appear here.
                </p>
              )
            )}
          </CardContent>
        </Card>

      </main>
    </div>
  );
};

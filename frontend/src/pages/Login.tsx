import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import apiClient from '../services/apiClient';

const Login: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const handleLogin = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setLoading(true);
    
    try {
      // API call to backend authentication
      const response = await apiClient.post('/auth/login', { email, password });
      
      const { accessToken } = response.data;
      
      if (accessToken) {
        localStorage.setItem('token', accessToken);
        // Add navigation based on role later
        navigate('/dashboard');
      }
    } catch (err: any) {
      setError(err.response?.data?.title || err.response?.data?.message || 'Tizimga kirishda xatolik yuz berdi. Email yoki parol noto‘g‘ri.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-[url('https://images.unsplash.com/photo-1534438327276-14e5300c3a48?q=80&w=2070')] bg-cover bg-center">
      <div className="absolute inset-0 bg-black/40 backdrop-blur-sm"></div>
      
      <div className="relative z-10 w-full max-w-md p-8 glass rounded-2xl shadow-2xl">
        <div className="text-center mb-8">
          <h1 className="text-3xl font-bold text-white mb-2 tracking-tight">UniSport</h1>
          <p className="text-white/80 text-sm">Universitet sport platformasiga xush kelibsiz</p>
        </div>

        {error && (
          <div className="mb-4 p-3 rounded-lg bg-destructive/90 text-white text-sm border border-destructive/20 text-center">
            {error}
          </div>
        )}

        <form onSubmit={handleLogin} className="space-y-5">
          <div>
            <label className="block text-sm font-medium text-white/90 mb-1.5" htmlFor="email">
              Elektron pochta
            </label>
            <input
              id="email"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="w-full px-4 py-2.5 rounded-xl bg-white/10 border border-white/20 text-white placeholder:text-white/40 focus:outline-none focus:ring-2 focus:ring-white/50 transition-all duration-200"
              placeholder="talaba@university.edu"
              required
            />
          </div>

          <div>
            <label className="block text-sm font-medium text-white/90 mb-1.5" htmlFor="password">
              Parol
            </label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="w-full px-4 py-2.5 rounded-xl bg-white/10 border border-white/20 text-white placeholder:text-white/40 focus:outline-none focus:ring-2 focus:ring-white/50 transition-all duration-200"
              placeholder="••••••••"
              required
            />
          </div>

          <button
            type="submit"
            disabled={loading}
            className="w-full py-3 px-4 rounded-xl font-medium text-black bg-white hover:bg-white/90 focus:outline-none focus:ring-2 focus:ring-white/50 focus:ring-offset-2 focus:ring-offset-transparent transition-all duration-300 disabled:opacity-50 mt-4 shadow-lg flex items-center justify-center gap-2"
          >
            {loading ? (
              <span className="w-5 h-5 border-2 border-black/20 border-t-black rounded-full animate-spin"></span>
            ) : (
              'Tizimga kirish'
            )}
          </button>
        </form>
        
        <div className="mt-6 text-center text-sm text-white/60">
          Hisobingiz yo'qmi? Admin bilan bog'laning.
        </div>
      </div>
    </div>
  );
};

export default Login;

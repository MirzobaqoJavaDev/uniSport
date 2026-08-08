import React from 'react';
import { Outlet, Navigate, useNavigate } from 'react-router-dom';
import { LogOut, Home, Calendar, Dumbbell, User } from 'lucide-react';

const DashboardLayout: React.FC = () => {
  const token = localStorage.getItem('token');
  const navigate = useNavigate();

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  const navItems = [
    { name: 'Asosiy', icon: <Home size={20} />, path: '/dashboard' },
    { name: 'Band qilish', icon: <Calendar size={20} />, path: '/dashboard/bookings' },
    { name: 'Mashg\'ulotlar', icon: <Dumbbell size={20} />, path: '/dashboard/workouts' },
    { name: 'Profil', icon: <User size={20} />, path: '/dashboard/profile' },
  ];

  return (
    <div className="min-h-screen bg-slate-50 flex">
      {/* Sidebar */}
      <aside className="w-64 bg-white border-r border-slate-200 shadow-sm hidden md:flex flex-col">
        <div className="p-6 border-b border-slate-100 flex items-center justify-center">
          <h1 className="text-2xl font-bold bg-gradient-to-r from-blue-600 to-indigo-600 bg-clip-text text-transparent tracking-tight">
            UniSport
          </h1>
        </div>
        
        <nav className="flex-1 p-4 space-y-2 overflow-y-auto">
          {navItems.map((item, idx) => (
            <button
              key={idx}
              className="w-full flex items-center gap-3 px-4 py-3 rounded-xl text-slate-600 hover:text-blue-600 hover:bg-blue-50 transition-all duration-200 group text-sm font-medium"
            >
              <div className="text-slate-400 group-hover:text-blue-500 transition-colors">
                {item.icon}
              </div>
              {item.name}
            </button>
          ))}
        </nav>
        
        <div className="p-4 border-t border-slate-100">
          <button
            onClick={handleLogout}
            className="w-full flex items-center gap-3 px-4 py-3 rounded-xl text-slate-600 hover:text-red-600 hover:bg-red-50 transition-all duration-200 font-medium text-sm"
          >
            <LogOut size={20} className="text-slate-400" />
            Tizimdan chiqish
          </button>
        </div>
      </aside>

      {/* Main content */}
      <main className="flex-1 flex flex-col h-screen overflow-hidden relative">
        {/* Top header (Mobile visible) */}
        <header className="h-16 bg-white border-b border-slate-200 flex items-center px-6 shadow-sm z-10">
          <div className="md:hidden font-bold text-xl text-blue-600">UniSport</div>
          <div className="ml-auto flex items-center gap-4">
            <div className="w-9 h-9 rounded-full bg-blue-100 text-blue-600 flex items-center justify-center font-bold text-sm shadow-sm ring-2 ring-white cursor-pointer hover:ring-blue-100 transition-all">
              US
            </div>
          </div>
        </header>
        
        {/* Page Content */}
        <div className="flex-1 overflow-y-auto p-6 relative">
          {/* Subtle background glow effect */}
          <div className="absolute top-0 right-0 w-96 h-96 bg-blue-400/5 rounded-full blur-3xl pointer-events-none"></div>
          
          <div className="relative z-10 max-w-6xl mx-auto">
            <Outlet />
          </div>
        </div>
      </main>
    </div>
  );
};

export default DashboardLayout;

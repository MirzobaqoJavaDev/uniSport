import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import Login from './pages/Login';
import DashboardLayout from './layouts/DashboardLayout';
import Bookings from './pages/Bookings';
import Workouts from './pages/Workouts';
import Profile from './pages/Profile';

// Dumy Dashboard Home page component
const DashboardHome = () => (
  <div>
    <h2 className="text-3xl font-bold text-slate-800 tracking-tight mb-2">Xush kelibsiz!</h2>
    <p className="text-slate-500 mb-8">Platformadagi bugungi statistikangiz va vazifalaringiz.</p>
    
    <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
      {[
        { label: 'Faol bandliklar', value: '2', color: 'bg-blue-50 text-blue-700' },
        { label: 'Yakunlangan mashqlar', value: '14', color: 'bg-emerald-50 text-emerald-700' },
        { label: 'Ijaradagi inventar', value: '1', color: 'bg-amber-50 text-amber-700' },
      ].map((stat, i) => (
        <div key={i} className="bg-white p-6 rounded-2xl shadow-sm border border-slate-100 hover:shadow-md transition-shadow">
          <p className="text-slate-500 text-sm font-medium mb-1">{stat.label}</p>
          <p className="text-3xl font-bold text-slate-800">{stat.value}</p>
        </div>
      ))}
    </div>
  </div>
);

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<DashboardLayout />}>
          <Route index element={<DashboardHome />} />
          <Route path="bookings" element={<Bookings />} />
          <Route path="workouts" element={<Workouts />} />
          <Route path="profile" element={<Profile />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;

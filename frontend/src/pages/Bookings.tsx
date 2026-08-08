import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { Calendar, Clock, MapPin } from 'lucide-react';
// import apiClient from '../services/apiClient';

interface Booking {
  id: string;
  title: string;
  date: string;
  time: string;
  location: string;
  status: 'ACTIVE' | 'COMPLETED' | 'CANCELLED';
}

const mockBookings: Booking[] = [
  {
    id: '1',
    title: 'Basketbol zali (Asosiy bino)',
    date: '2026-08-10',
    time: '14:00 - 15:30',
    location: 'Zal A',
    status: 'ACTIVE',
  },
  {
    id: '2',
    title: 'Suzish havzasi',
    date: '2026-08-11',
    time: '09:00 - 10:00',
    location: 'Bino C',
    status: 'ACTIVE',
  },
  {
    id: '3',
    title: 'Stol tennisi raketkasi',
    date: '2026-08-01',
    time: '16:00 - 17:00',
    location: 'Inventar xonasi',
    status: 'COMPLETED',
  }
];

const Bookings: React.FC = () => {
  // const { data, isLoading } = useQuery({
  //   queryKey: ['bookings'],
  //   queryFn: async () => {
  //     const res = await apiClient.get('/bookings');
  //     return res.data;
  //   }
  // });

  const data = mockBookings;
  const isLoading = false;

  if (isLoading) {
    return (
      <div className="flex items-center justify-center h-64">
        <div className="w-8 h-8 border-4 border-blue-500 border-t-transparent rounded-full animate-spin"></div>
      </div>
    );
  }

  return (
    <div>
      <div className="flex items-center justify-between mb-8">
        <div>
          <h2 className="text-3xl font-bold text-slate-800 tracking-tight">Band qilishlar</h2>
          <p className="text-slate-500 mt-1">Sizning faol va oldingi band qilingan mashg'ulotlaringiz.</p>
        </div>
        <button className="bg-blue-600 hover:bg-blue-700 text-white px-5 py-2.5 rounded-xl font-medium transition-colors shadow-sm shadow-blue-600/20">
          Yangi band qilish
        </button>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {data.map((booking) => (
          <div key={booking.id} className="bg-white rounded-2xl p-6 shadow-sm border border-slate-100 hover:shadow-md transition-all relative overflow-hidden group">
            <div className={`absolute top-0 left-0 w-1 h-full ${
              booking.status === 'ACTIVE' ? 'bg-blue-500' : 
              booking.status === 'COMPLETED' ? 'bg-emerald-500' : 'bg-red-500'
            }`}></div>
            
            <div className="flex justify-between items-start mb-4">
              <h3 className="font-semibold text-slate-800 text-lg group-hover:text-blue-600 transition-colors">
                {booking.title}
              </h3>
              <span className={`px-2.5 py-1 rounded-full text-xs font-medium ${
                booking.status === 'ACTIVE' ? 'bg-blue-50 text-blue-700' : 
                booking.status === 'COMPLETED' ? 'bg-emerald-50 text-emerald-700' : 'bg-red-50 text-red-700'
              }`}>
                {booking.status}
              </span>
            </div>

            <div className="space-y-3 text-slate-600 text-sm">
              <div className="flex items-center gap-2">
                <Calendar size={16} className="text-slate-400" />
                <span>{booking.date}</span>
              </div>
              <div className="flex items-center gap-2">
                <Clock size={16} className="text-slate-400" />
                <span>{booking.time}</span>
              </div>
              <div className="flex items-center gap-2">
                <MapPin size={16} className="text-slate-400" />
                <span>{booking.location}</span>
              </div>
            </div>
            
            {booking.status === 'ACTIVE' && (
              <div className="mt-6 pt-4 border-t border-slate-100 flex gap-3">
                <button className="flex-1 bg-slate-50 hover:bg-slate-100 text-slate-700 py-2 rounded-lg font-medium text-sm transition-colors border border-slate-200">
                  Bekor qilish
                </button>
                <button className="flex-1 bg-blue-50 hover:bg-blue-100 text-blue-700 py-2 rounded-lg font-medium text-sm transition-colors">
                  Batafsil
                </button>
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
};

export default Bookings;

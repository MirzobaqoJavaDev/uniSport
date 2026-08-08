import React from 'react';
import { User, Mail, Shield, Key, Bell } from 'lucide-react';

const Profile: React.FC = () => {
  return (
    <div className="max-w-4xl mx-auto">
      <div className="mb-8">
        <h2 className="text-3xl font-bold text-slate-800 tracking-tight">Profil</h2>
        <p className="text-slate-500 mt-1">Shaxsiy ma'lumotlaringiz va tizim sozlamalari.</p>
      </div>

      <div className="bg-white rounded-2xl shadow-sm border border-slate-100 overflow-hidden">
        {/* Header/Cover */}
        <div className="h-32 bg-gradient-to-r from-blue-500 to-indigo-600 relative">
          <div className="absolute -bottom-12 left-8">
            <div className="w-24 h-24 bg-white rounded-full p-1 shadow-md">
              <div className="w-full h-full bg-slate-100 rounded-full flex items-center justify-center text-slate-400">
                <User size={40} />
              </div>
            </div>
          </div>
        </div>

        {/* Content */}
        <div className="pt-16 pb-8 px-8">
          <div className="flex justify-between items-start mb-8">
            <div>
              <h3 className="text-2xl font-bold text-slate-800">Talaba Ismi</h3>
              <p className="text-slate-500 flex items-center gap-2 mt-1">
                <Shield size={16} className="text-emerald-500" />
                Oddiy Talaba (ROLE_USER)
              </p>
            </div>
            <button className="bg-slate-100 hover:bg-slate-200 text-slate-700 px-4 py-2 rounded-lg font-medium text-sm transition-colors">
              Tahrirlash
            </button>
          </div>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
            <div className="space-y-6">
              <h4 className="font-semibold text-slate-800 border-b border-slate-100 pb-2">Shaxsiy Ma'lumotlar</h4>
              
              <div className="space-y-4">
                <div>
                  <label className="text-xs font-semibold text-slate-400 uppercase tracking-wider block mb-1">Elektron pochta</label>
                  <div className="flex items-center gap-3 text-slate-700 bg-slate-50 p-3 rounded-xl border border-slate-100">
                    <Mail size={18} className="text-slate-400" />
                    talaba@university.edu
                  </div>
                </div>
                
                <div>
                  <label className="text-xs font-semibold text-slate-400 uppercase tracking-wider block mb-1">Universitet ID</label>
                  <div className="flex items-center gap-3 text-slate-700 bg-slate-50 p-3 rounded-xl border border-slate-100">
                    <User size={18} className="text-slate-400" />
                    U2026-0810
                  </div>
                </div>
              </div>
            </div>

            <div className="space-y-6">
              <h4 className="font-semibold text-slate-800 border-b border-slate-100 pb-2">Xavfsizlik va Sozlamalar</h4>
              
              <div className="space-y-3">
                <button className="w-full flex items-center justify-between p-3 rounded-xl hover:bg-slate-50 transition-colors border border-transparent hover:border-slate-100 group">
                  <div className="flex items-center gap-3 text-slate-700">
                    <div className="w-8 h-8 rounded-full bg-blue-50 flex items-center justify-center text-blue-600 group-hover:bg-blue-100 transition-colors">
                      <Key size={16} />
                    </div>
                    <span className="font-medium text-sm">Parolni o'zgartirish</span>
                  </div>
                  <span className="text-slate-400">→</span>
                </button>

                <button className="w-full flex items-center justify-between p-3 rounded-xl hover:bg-slate-50 transition-colors border border-transparent hover:border-slate-100 group">
                  <div className="flex items-center gap-3 text-slate-700">
                    <div className="w-8 h-8 rounded-full bg-indigo-50 flex items-center justify-center text-indigo-600 group-hover:bg-indigo-100 transition-colors">
                      <Bell size={16} />
                    </div>
                    <span className="font-medium text-sm">Bildirishnomalar</span>
                  </div>
                  <span className="text-slate-400">→</span>
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;

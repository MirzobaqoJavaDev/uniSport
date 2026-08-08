import React, { useState } from 'react';
import { Activity, Dumbbell, Flame, BotMessageSquare } from 'lucide-react';
// import apiClient from '../services/apiClient';

const mockWorkouts = [
  { id: '1', name: 'Ertalabki yugurish', duration: '30 min', calories: 320, date: '2026-08-08', type: 'Kardio' },
  { id: '2', name: 'Ogir atletika (Zal)', duration: '60 min', calories: 450, date: '2026-08-07', type: 'Kuch' },
  { id: '3', name: 'Suzish', duration: '45 min', calories: 500, date: '2026-08-05', type: 'Aralash' },
];

const Workouts: React.FC = () => {
  const [aiQuery, setAiQuery] = useState('');
  const [aiResponse, setAiResponse] = useState('');
  const [isAiLoading, setIsAiLoading] = useState(false);

  const handleAskAI = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!aiQuery.trim()) return;

    setIsAiLoading(true);
    // TODO: Connect to backend AI endpoint
    // const response = await apiClient.post('/ai/chat', { prompt: aiQuery });
    // setAiResponse(response.data.answer);
    
    // Mock response for now
    setTimeout(() => {
      setAiResponse('Sizning ma\'lumotlaringizga asoslanib, mushaklarni tiklash uchun ko\'proq oqsil iste\'mol qilishingiz va kamida 8 soat uxlashingiz tavsiya etiladi. Esda tuting: Tibbiy holatlar bo\'yicha shifokorga murojaat qiling.');
      setIsAiLoading(false);
    }, 1500);
  };

  return (
    <div className="space-y-8">
      <div className="flex items-center justify-between">
        <div>
          <h2 className="text-3xl font-bold text-slate-800 tracking-tight">Mashg'ulotlar</h2>
          <p className="text-slate-500 mt-1">Sizning jismoniy faolligingiz va AI tavsiyalari.</p>
        </div>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        
        {/* Left Column - Workout History */}
        <div className="lg:col-span-2 space-y-6">
          <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-100">
            <h3 className="text-lg font-semibold text-slate-800 mb-4 flex items-center gap-2">
              <Activity className="text-blue-500" size={20} />
              So'nggi mashg'ulotlar
            </h3>
            
            <div className="space-y-4">
              {mockWorkouts.map(workout => (
                <div key={workout.id} className="flex items-center justify-between p-4 rounded-xl border border-slate-100 hover:border-blue-100 hover:bg-blue-50/50 transition-colors">
                  <div className="flex items-center gap-4">
                    <div className="w-10 h-10 rounded-full bg-blue-100 flex items-center justify-center text-blue-600">
                      {workout.type === 'Kardio' ? <Activity size={18} /> : workout.type === 'Kuch' ? <Dumbbell size={18} /> : <Flame size={18} />}
                    </div>
                    <div>
                      <h4 className="font-medium text-slate-800">{workout.name}</h4>
                      <p className="text-xs text-slate-500">{workout.date} • {workout.type}</p>
                    </div>
                  </div>
                  <div className="text-right">
                    <div className="font-semibold text-slate-700">{workout.duration}</div>
                    <div className="text-xs text-orange-500 flex items-center justify-end gap-1">
                      <Flame size={12} /> {workout.calories} kcal
                    </div>
                  </div>
                </div>
              ))}
            </div>
            
            <button className="w-full mt-4 py-2.5 text-sm font-medium text-blue-600 hover:bg-blue-50 rounded-lg transition-colors">
              Barcha mashg'ulotlarni ko'rish
            </button>
          </div>
        </div>

        {/* Right Column - AI Assistant */}
        <div className="lg:col-span-1">
          <div className="bg-gradient-to-br from-indigo-500 to-purple-600 p-6 rounded-2xl shadow-md text-white relative overflow-hidden">
            <div className="absolute top-0 right-0 w-32 h-32 bg-white/10 rounded-full blur-2xl -mr-10 -mt-10"></div>
            
            <h3 className="text-lg font-semibold mb-2 flex items-center gap-2 relative z-10">
              <BotMessageSquare size={20} />
              AI Murabbiy
            </h3>
            <p className="text-indigo-100 text-sm mb-6 relative z-10">
              Sog'lig'ingiz va mashqlar bo'yicha maslahatlar oling.
            </p>

            <form onSubmit={handleAskAI} className="relative z-10 space-y-3">
              <textarea
                value={aiQuery}
                onChange={(e) => setAiQuery(e.target.value)}
                placeholder="Menga yugurish uchun dastur tuzib bering..."
                className="w-full bg-white/10 border border-white/20 rounded-xl p-3 text-sm text-white placeholder:text-indigo-200 focus:outline-none focus:ring-2 focus:ring-white/50 resize-none h-24"
              ></textarea>
              <button 
                type="submit"
                disabled={isAiLoading || !aiQuery.trim()}
                className="w-full bg-white text-indigo-600 hover:bg-indigo-50 font-medium py-2.5 rounded-xl transition-colors disabled:opacity-70 disabled:cursor-not-allowed flex justify-center items-center h-10"
              >
                {isAiLoading ? (
                  <div className="w-5 h-5 border-2 border-indigo-600/30 border-t-indigo-600 rounded-full animate-spin"></div>
                ) : 'So\'rash'}
              </button>
            </form>

            {aiResponse && (
              <div className="mt-6 bg-white/10 border border-white/20 rounded-xl p-4 relative z-10 animate-in fade-in slide-in-from-bottom-2">
                <p className="text-sm text-indigo-50 leading-relaxed">
                  {aiResponse}
                </p>
                <div className="mt-3 pt-3 border-t border-white/10">
                  <p className="text-[10px] text-indigo-200 uppercase tracking-wider font-semibold opacity-80">
                    ⚠️ Diqqat: Bu tibbiy maslahat emas. Maxsus holatlarda shifokorga murojaat qiling.
                  </p>
                </div>
              </div>
            )}
          </div>
        </div>

      </div>
    </div>
  );
};

export default Workouts;

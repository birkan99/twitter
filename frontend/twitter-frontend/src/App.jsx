import './App.css';
import TweetList from './components/TweetList';
import { useState, useEffect } from 'react';

function App() {
  const [currentUserId, setCurrentUserId] = useState(null);
  const [refreshKey, setRefreshKey] = useState(0); // Yenileme tetikleyicisi

  useEffect(() => {
    const savedUserId = localStorage.getItem('currentUserId');
    setCurrentUserId(savedUserId || 1);
  }, []);

  // Yeni tweet atıldığında çağrılacak fonksiyon
  const handleTweetAdded = () => {
    setRefreshKey(prev => prev + 1);
  };

  return (
    <div className="layout">
      <header className="header">
        <h2>Anasayfa</h2>
      </header>

      <main>
        {/* Tweet Atma Bileşenin Varsa handleTweetAdded propunu ona geçmelisin */}
        {/* Örn: <TweetPost onTweetAdded={handleTweetAdded} /> */}

        {currentUserId && (
          <TweetList
            userId={currentUserId}
            refreshKey={refreshKey} // Listeye anahtarı gönderiyoruz
          />
        )}
      </main>
    </div>
  );
}

export default App;
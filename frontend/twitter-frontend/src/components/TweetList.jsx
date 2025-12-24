import { useEffect, useState } from 'react';
import api from '../api/axios';

const TweetList = ({ userId, refreshKey }) => {
  const [tweets, setTweets] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchTweets = async () => {
      setLoading(true);
      try {
        const res = await api.get('/tweet');
        console.log("Başarılı! Gelen Tweetler:", res.data);
        setTweets(res.data || []);
      } catch (err) {
        console.error("Tweetler yüklenirken hata:", err?.response?.status, err?.response?.data);
      } finally {
        setLoading(false);
      }
    };

    fetchTweets();
  }, [refreshKey]);

  // Basit "x dakika/saat önce" fonksiyonu
  const timeAgo = (isoString) => {
    if (!isoString) return '';
    const date = new Date(isoString);
    const now = new Date();
    const diff = Math.floor((now - date) / 1000); // saniye
    if (diff < 60) return `${diff} s`;
    if (diff < 3600) return `${Math.floor(diff / 60)} dk`;
    if (diff < 86400) return `${Math.floor(diff / 3600)} sa`;
    return date.toLocaleString('tr-TR', { day: '2-digit', month: '2-digit', year: 'numeric' });
  };

  const formatFull = (isoString) => {
    if (!isoString) return '';
    const date = new Date(isoString);
    return date.toLocaleString('tr-TR', { dateStyle: 'medium', timeStyle: 'short' });
  };

  const initials = (name = '') => {
    return name.split(' ').map(n => n[0]).slice(0,2).join('').toUpperCase() || '?';
  };

  return (
    <div className="tweets-wrapper">
      {loading ? (
        <div className="loading">Yükleniyor...</div>
      ) : tweets.length === 0 ? (
        <div className="empty">Gösterilecek tweet yok.</div>
      ) : (
        tweets.map((tweet) => (
          <div key={tweet.id} className="tweet-card" title={formatFull(tweet.created_at)}>
            <div className="avatar">{initials(tweet.display_name)}</div>
            <div className="tweet-body">
              <div className="tweet-header">
                <div className="meta-left">
                  <span className="display-name">{tweet.display_name}</span>
                  <span className="username"> @{tweet.display_name?.toLowerCase().replace(/\s/g, '')}</span>
                </div>
                <div className="meta-right">
                  <span className="time">{timeAgo(tweet.created_at)}</span>
                </div>
              </div>
              <div className="tweet-content">{tweet.content}</div>
            </div>
          </div>
        ))
      )}
    </div>
  );
};

export default TweetList;

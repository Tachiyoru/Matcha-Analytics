import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Home.css';

const Home = () => {
  const [users, setUsers] = useState([]);
  const [selectedUser, setSelectedUser] = useState(null);

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const response = await axios.get('http://localhost:5002/api/analytics/users');
      setUsers(response.data);
    } catch (error) {
      console.error('Error fetching users:', error);
    }
  };

  return (
    <div className="home-container">
      <h1>Welcome to Matcha-Analytics</h1>
      
      <div className="content-wrapper">
        <div className="users-list">
          <h2>Users List</h2>
          <div className="users-grid">
            {users.map(user => (
              <div 
                key={user.id} 
                className={`user-card ${selectedUser?.id === user.id ? 'selected' : ''}`}
                onClick={() => setSelectedUser(user)}
              >
                <div className="user-avatar">
                  {user.username.charAt(0).toUpperCase()}
                </div>
                <div className="user-info">
                  <h3>{user.username}</h3>
                  <p>{user.email}</p>
                </div>
              </div>
            ))}
          </div>
        </div>

        {selectedUser && (
          <div className="user-details">
            <h2>User Details</h2>
            <div className="detail-card">
              <div className="detail-header">
                <div className="large-avatar">
                  {selectedUser.username.charAt(0).toUpperCase()}
                </div>
                <h3>{selectedUser.username}</h3>
              </div>
              <div className="detail-info">
                <p><strong>Email:</strong> {selectedUser.email}</p>
                <p><strong>ID:</strong> {selectedUser.id}</p>
                <p><strong>Created At:</strong> {new Date(selectedUser.createdAt).toLocaleDateString()}</p>
                <p><strong>Last Login:</strong> {selectedUser.lastLogin ? new Date(selectedUser.lastLogin).toLocaleString() : 'Never'}</p>
                <p><strong>Status:</strong> <span className={`status ${selectedUser.active ? 'active' : 'inactive'}`}>
                  {selectedUser.active ? 'Active' : 'Inactive'}
                </span></p>
              </div>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default Home; 
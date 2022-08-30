import React, { Component } from 'react';
import './App.css';
import Home from './Home';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import BookList from './BookList';
import BookEdit from './BookEdit';
import Api from './Api';
import NavBar from './NavBar';

const api = new Api();

class App extends Component {

  render() {
    const navbar = <NavBar/>;

    return (
      <Router>
        <Routes>
          <Route
            path='/'
            element={<Home api={api} navbar={navbar}/>}
          />
          <Route
            path='/book-list'
            element={<BookList api={api} navbar={navbar}/>}
          />
          <Route
            path='/book-list/:id'
            element={<BookEdit api={api} navbar={navbar}/>}
          />
        </Routes>
      </Router>
    )
  }
}

export default App;

/*
function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
*/
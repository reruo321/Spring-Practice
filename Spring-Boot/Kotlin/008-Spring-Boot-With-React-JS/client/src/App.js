import logo from './logo.svg';
import React, { Component } from 'react';
import './App.css';

class App extends Component {
  state = {
    isLoading: true,
    books: []
  };

  async componentDidMount() {
    const response = await fetch('/api/books');
    const body = await response.json();
    this.setState({books: body._embedded.books, isLoading: false});
  }

  render() {
    const {books, isLoading} = this.state;

    if (isLoading) {
      return <p>Loading...</p>;
    }

    return (
      <div className="App">
        <header className="App-header">
          <div className="App-intro">
            <h2>Book List</h2>
            {books.map(book =>
              <div key={book.id}>
                {book.name} - {book.address}
              </div>
            )}
          </div>
        </header>
      </div>
    );
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
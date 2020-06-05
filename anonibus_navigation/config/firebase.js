import firebase from 'firebase';
import "firebase/auth";
import "firebase/firestore";
import "firebase/storage";

const firebaseConfig = {
  apiKey: "AIzaSyACJ5whxaz35ZH5EVSCeZRvBvfz4vQ34Ag",
  authDomain: "anonibus-7d049.firebaseapp.com",
  databaseURL: "https://anonibus-7d049.firebaseio.com",
  projectId: "anonibus-7d049",
  storageBucket: "anonibus-7d049.appspot.com",
  messagingSenderId: "894575957254",
  appId: "1:894575957254:web:873ace8e6aefe9c313ba87"
};

export default !firebase.apps.length ? firebase.initializeApp(firebaseConfig) : firebase.app();
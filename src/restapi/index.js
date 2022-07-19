const express = require("express");
const bodyParser = require('body-parser')

const app = require('express')();
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: true}));
//Importing postgres nodejs
const { Client } = require("pg");

//Connecting to the database
const client = new Client({
    host: "localhost",
    user: "postgres",
    port: 5432,
    password: "SWSCswsc",
    database: "messageapp"
})

client.connect();

//Enabling JSON
app.use(express.json())

//Create the server listening on port 3000.
app.listen(3000, () => {
    console.log("Listening on port 3000...")
})

//When the server receives a 'register' POST request from anything, respond with a json object containing 'yes' back to the sender.
app.post('/register', (req, res) => {

    // client.query('SELECT nextval(\'users_user_id_seq\')', (err, res) => {
    // })

    var data_username = req.body.username
    var data_password = req.body.password
    var data_email = req.body.email
    var data_created_on = 'NOW()'

    const query = {
        text: 'INSERT INTO users(username, password, email, created_on) VALUES($1, $2, $3, $4)',
        values: [data_username, data_password, data_email, data_created_on],
    }

    client.query(query, (err, res) => {
        if(!err){
            console.log("Creation Successful");
        } else{
            console.log(err.message);
        }
    })

    //Phone checks to see if it receives a yes which shows the POST and RESPONSE worked.
    res.json('yes')
})

//Prints all the data in the users table.
client.query('SELECT * FROM users', (err, res) => {
    if(!err){
        console.log(res.rows);
    } else{
        console.log(err.message);
    }
})
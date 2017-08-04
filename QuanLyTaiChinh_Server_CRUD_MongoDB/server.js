// dependencies
var bodyParser = require("body-parser");
var mongodb = require("mongodb"); 
ObjectId = require('mongodb').ObjectID;
var mongoose = require('mongoose');

var express = require("express");
var app = express();
var http = require("http").Server(app);
var io = require("socket.io")(http);


var MongoClient = mongodb.MongoClient;
var url = 'mongodb://localhost:27017/android_quanlytaichinh';

mongoose.connect(url);

MongoClient.connect(url, (error, db) =>{
    if (error)
    {
        console.log("Unable to connect to MongoDB server. Error: ", error);
    }
    else
    {
        console.log("Connection established to ", url);
        collection = db.collection('customers');

       
       // Khi Chỉnh Sửa Dữ Liệu Trong DB Thì Vui Lòng Xóa DB Cũ Trong RoboMongo 
        collection.insert(
                    [{'lastname':'admin', 'firstname':'admin', 'email':'admin@gmail.com','password':'admin', 'DateOfBirth':'10/10/2020'},
                    {'lastname':'Nguyen', 'firstname':'Thi Quynh Hoa', 'email':'hoantq@gmail.com','password':'hoantq', 'DateOfBirth':'10/10/2020'},
                    {'lastname':'Nguyen', 'firstname':'Thi Quynh Mai', 'email':'maintq@gmail.com','password':'maintq', 'DateOfBirth':'10/10/2022'}],
                     (docs) => 
                    {
                        // Count the number of records
                        collection.count((err, count) => {
                            console.log("There are " + count + " records.");
                        });
                    });

         collectionFinancial = db.collection('financial_types');
         collectionFinancial.insert(
                    [{'typename':'Tiền Điện'}, {'typename':'Tiền Nước'},{'typename':'Học Phí'},{'typename':'Từ Thiện'},
                    {'typename':'Chứng khoán'},{'typename':' Đầu tư con cái‎'},{'typename':'Cho Vay'},{'typename':'Lãi Ngân Hàng'},
                    {'typename':'Trái phiếu'},{'typename':'Tiền Lương'},{'typename':'Chi Tiêu Cá Nhân'},{'typename':'Y Tế'},
                    {'typename':'Giáo Dục'},{'typename':'Bất Động Sản'},{'typename':'Tiền Giải Trí Vui Chơi'},{'typename':'Tiền Duy Trì Hoạt Động Công Ty'}],
                     (docs) => 
                    {
                        // Count the number of records
                        collection.count((err, count) => {
                            console.log("There are " + count + " Records Financial.");
                        });
                    });

         collectionTransaction = db.collection('transactions');
         collectionTransaction.insert(
                    [{'money':'10 000 000','category':'Tiền Điện', 'title':'Đóng Tiền Điện', 'type_account':'Tài Khoản Cá Nhân', 'note':'Đóng Tiền Điện'},
                    {'money':'10 000 000','category':'Tiền Nước', 'title':'Đóng Tiền Nước', 'type_account':'Tài Khoản Cá Nhân', 'note':'Đóng Tiền Nước'},
                    {'money':'10 000 000','category':'Học Phí', 'title':'Đóng Học Phí', 'type_account':'Tài Khoản Cá Nhân', 'note':'Đóng Học Phí'},
                    {'money':'10 000 000','category':'Từ Thiện', 'title':'Đóng Từ Thiện', 'type_account':'Tài Khoản Cá Nhân', 'note':'Tiền Từ Thiện'},
                    {'money':'10 000 000','category':'Chứng khoán', 'title':'Chứng khoán', 'type_account':'Tài Khoản Cá Nhân', 'note':'Chứng khoán'},
                    {'money':'10 000 000','category':'Đầu tư con cái', 'title':'Đầu tư con cái', 'type_account':'Tài Khoản Cá Nhân', 'note':'Đầu tư con cái'},
                    {'money':'10 000 000','category':'Cho Vay', 'title':'Cho Vay', 'type_account':'Tài Khoản Cá Nhân', 'note':'Cho Vay'},
                    {'money':'10 000 000','category':'Lãi Ngân Hàng', 'title':'Lãi Ngân Hàng', 'type_account':'Tài Khoản Cá Nhân', 'note':'Lãi Ngân Hàng'},
                    {'money':'10 000 000','category':'Trái phiếu', 'title':'Trái phiếu', 'type_account':'Tài Khoản Cá Nhân', 'note':'Trái phiếu'},
                    {'money':'10 000 000','category':'Tiền Lương', 'title':'Tiền Lương', 'type_account':'Tài Khoản Cá Nhân', 'note':'Tiền Lương'},
                    {'money':'10 000 000','category':'Chi Tiêu Cá Nhân', 'title':'Chi Tiêu Cá Nhân', 'type_account':'Tài Khoản Cá Nhân', 'note':'Chi Tiêu Cá Nhân'},
                    {'money':'10 000 000','category':'Bất Động Sản', 'title':'Bất Động Sản', 'type_account':'Tài Khoản Cá Nhân', 'note':'Bất Động Sản'},
                    {'money':'10 000 000 000','category':'Tiền Duy Trì Hoạt Động Công Ty', 'title':'Tiền Duy Trì Hoạt Động Công Ty', 'type_account':'Tài Khoản Cá Nhân', 'note':'Tiền Duy Trì Hoạt Động Công Ty'}],
                     (docs) => 
                    {
                        // Count the number of records
                        collection.count((err, count) => {
                            console.log("There are " + count + " Records Financial.");
                        });
                    });

    }
});

// Code Tạo Bảng
var finacial_type   = require('./financial_type.model');
var customer_type   = require('./customer_type.model');
var customer        = require('./customer.model');
var transaction     = require('./transaction.model');

app.get('/customer', (req, res) =>{

    customer.find({}).exec((err, customers) =>{
        if (err)
            res.send("Error has occured");
        else
            res.json(customers);
    });
});

app.get('/financial', (req, res) =>{

    finacial_type.find({}).exec((err, finacial_type) =>{
        if (err)
            res.send("Error has occured");
        else
            res.json(finacial_type);
    });
});

app.get('/personal_type', (req, res) =>{

    transaction.find({}).exec((err, transaction) =>{
        if (err)
            res.send("Error has occured");
        else
            res.json(transaction);
    });
});

app.get('/customer/:id', (req, res) =>{

    customer.findOne({
        _id: req.params.id
    }).exec((err, customers) =>{
        if (err)
            res.send("Error has occured");
        else
            res.json(customers);
    });
});



io.on('connection', (socket) =>{
    
     socket.on('updateTransaction', (id, money, category, title, note) =>{
        MongoClient.connect(url, function(err, db) {
        if (err) 
            throw err;
        var myquery = { '_id': ObjectId(id) };
        var newvalues = {$set: {'money': money, 'category': category, 'title': title, 'note': note } };
        db.collection("transactions").update(myquery, newvalues, function(err, res) {
           if (res.result.nModified == 0) {
                socket.emit('updateTransaction', false);
            }
            else
            {
                console.log(res.result.nModified + " record updated");
                socket.emit('updateTransaction', true);
            }
            db.close();
        });
        });
    });

    /*---------------------------------------------------------------------- */
    socket.on('deleteTransaction', (id) =>{
        MongoClient.connect(url, function(err, db) {
        if (err) 
            throw err;
        var myquery = { '_id': ObjectId(id)};
        db.collection("transactions").remove(myquery, function(err, res) {
            if (res.result.n == 0) 
            {
                socket.emit('deleteTransaction', false);
            }
            else
            {
                // Trả về số lượng document
                console.log(res.result.n + " document(s) deleted");
                socket.emit('deleteTransaction', true);
            }
            
            db.close();
        });
        });
    });

    /*------------------------------------------------------------------------ */
    socket.on('addTransaction', (money, category, title, note) =>{
        MongoClient.connect(url, (err, db) => {
            if (err) 
                throw err;

            var objTransaction 
                = { 
                    'money': money,
                    'category': category,
                    'title': title,
                    'type_account': 'Tài Khoản Cá Nhân',
                    'note': note,
                 };
            db.collection("transactions").insertOne(objTransaction, (err, res) => {
                if (err) 
                {
                    socket.emit('addTransaction', false);
                }
                else
                {
                    socket.emit('addTransaction', true);   
                    console.log("1 record inserted");
                }
                
                db.close();
            });
        });
    });

    /**************************************************************************************/
    socket.on('updateTypeFinancial', (oldType, newType) =>{
        MongoClient.connect(url, function(err, db) {
        if (err) 
            throw err;
        var myquery = { 'typename': oldType };
        var newvalues = {$set: {'typename': newType} };
        db.collection("financial_types").update(myquery, newvalues, function(err, res) {
           if (res.result.nModified == 0) {
                socket.emit('updateTypeFinancial', false);
            }
            else
            {
                console.log(res.result.nModified + " record updated");
                socket.emit('updateTypeFinancial', true);
            }
            db.close();
        });
        });
    });

    socket.on('deleteTypeFinancial', (type) =>{
        MongoClient.connect(url, function(err, db) {
        if (err) 
            throw err;
        var myquery = { 'typename': type };
        db.collection("financial_types").remove(myquery, function(err, res) {
            if (res.result.n == 0) 
            {
                socket.emit('deleteTypeFinancial', false);
            }
            else
            {
                // Trả về số lượng document
                console.log(res.result.n + " document(s) deleted");
                socket.emit('deleteTypeFinancial', true);
            }
            
            db.close();
        });
        });
    });

    socket.on('addTypeFinancial', (type) =>{
        MongoClient.connect(url, (err, db) => {
            if (err) 
                throw err;
            var objType 
                = { 
                    'typename': type 
                 };
            db.collection("financial_types").insertOne(objType, (err, res) => {
                if (err) 
                {
                    socket.emit('addTypeFinancial', false);
                }
                else
                {
                    socket.emit('addTypeFinancial', true);   
                    console.log("1 record inserted");
                }
                
                db.close();
            });
        });
    });

    /**************************************************************************************/
    socket.on('forgotPassword', (email, password) =>{
        MongoClient.connect(url, function(err, db) {
        if (err) 
            throw err;
        var myquery = { 'email': email };

        //To update only selected fields, $set operator needs to be used.
        var newvalues = {$set: {'password': password}};
        db.collection("customers").update(myquery, newvalues, function(err, res) {
            if (res.result.nModified == 0) {
                socket.emit('forgotPassword', false);
            }
            else
            {
                console.log(res.result.nModified + " record updated");
                socket.emit('forgotPassword', true);
            }   
            
            db.close();
        });
        });
    });

    socket.on('update', (emailOld, lastname, firstname, email) =>{
        MongoClient.connect(url, function(err, db) {
        if (err) 
            throw err;
        var myquery = { 'email': emailOld };
        var newvalues = {$set: {'lastname': lastname, 'firstname': firstname, 'email': email} };
        db.collection("customers").update(myquery, newvalues, function(err, res) {
           if (res.result.nModified == 0) {
                socket.emit('update', false);
            }
            else
            {
                console.log(res.result.nModified + " record updated");
                socket.emit('update', true);
            }
            db.close();
        });
        });
    });

   socket.on('delete', (email) =>{
        MongoClient.connect(url, function(err, db) {
        if (err) 
            throw err;
        var myquery = { 'email': email };
        db.collection("customers").remove(myquery, function(err, res) {
            if (res.result.n == 0) 
            {
                socket.emit('delete', false);
            }
            else
            {
                // Trả về số lượng document
                console.log(res.result.n + " document(s) deleted");
                socket.emit('delete', true);
            }
            
            db.close();
        });
        });
    });

    socket.on('register', (lastname, firstname, email, password, dateOfBirth) =>{
        MongoClient.connect(url, (err, db) => {
            if (err) 
                throw err;
            var objCustomer 
                = { 
                    'lastname': lastname, 'firstname': firstname,'email': email, 'password': password, 'DateOfBirth': dateOfBirth 
                 };
            db.collection("customers").insertOne(objCustomer, (err, res) => {
                if (err) 
                {
                    socket.emit('register', false);
                }
                else
                {
                    socket.emit('register', true);   
                    console.log("1 record inserted");
                }
                
                db.close();
            });
        });
    });
    
    socket.on('login', (email, password) =>{
        var cursor = collection.find({email: email});
     
        if (email != null){

          
            console.log(cursor + " login");
            cursor.each( (error, doc) => {
                if (error)
                {
                    console.log(error);
                    socket.emit('login', false);
                }
                else
                {
                    if (doc != null){
                        if (doc.password == password)
                        {
                            socket.emit('login', true);
                        }
                        else
                        {
                            socket.emit('login', false);
                        }
                    }
                }
            });
        }
        else
        {
            console.log("No exist username: ", email);
            socket.emit('login', false);
        }
           
       
        
    });
});

http.listen(3000, function(){
    console.log("Connecting on port 3000");
});

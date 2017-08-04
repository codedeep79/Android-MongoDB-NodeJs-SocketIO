'use strict'

var mongoose = require("mongoose");
var Schema = mongoose.Schema;

// Các cột có required lad bắt buộc phải làm
var loginSchema = new Schema({
    lastname: {
        type: String,
        required: true
    },
    firstname: {
        type: String,
        required: true
    },
    email:  {
        type: String,
        unique: true
    },
    password: {
        type: String,
        required: true
    },
    DateOfBirth: String,
    created_day:{
        type: Date,
        default: Date.now
    }
});

module.exports = mongoose.model('customer', loginSchema);
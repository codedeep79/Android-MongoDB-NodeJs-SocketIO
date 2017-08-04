'use strict'

var mongoose = require("mongoose");
var Schema = mongoose.Schema;

// Các cột có required lad bắt buộc phải làm
var schema = new Schema({
    money: {
        type: String,
        required: true
    },
    category: {
        type: String,
        required: true
    },
    title:  {
        type: String
    },
    type_account: {
        type: String,
        required: true
    },
    note: String,
    created_day:{
        type: Date,
        default: Date.now
    }
});

module.exports = mongoose.model('transaction', schema);
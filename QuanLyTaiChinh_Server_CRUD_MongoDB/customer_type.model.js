'use strict'

var mongoose = require("mongoose");
var Schema = mongoose.Schema;

var schema = new Schema({
    // Ít Nhất 3 Thuộc Tính 
    typename: {
        type: String,
        unique: true,
        required: true
    },
    created_day:{
        type: Date,
        default: Date.now
    }
});

module.exports = mongoose.model('customer_type', schema);
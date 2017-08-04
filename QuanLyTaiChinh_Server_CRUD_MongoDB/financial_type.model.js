'use strict'

var mongoose = require("mongoose");
var Schema = mongoose.Schema;

// Các cột có required lad bắt buộc phải làm
var schema = new Schema({
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

module.exports = mongoose.model('financial_type', schema);
package com.redpillsystems.jamfu.model

import com.google.appengine.api.datastore.Key
import javax.jdo.annotations.{IdGeneratorStrategy, Persistent, PrimaryKey, PersistenceCapable}

@PersistenceCapable
class User(@PrimaryKey
           @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY) val key: Key,
           @Persistent var username: String,
           @Persistent var firstName: String,
           @Persistent var lastName: String,
           @Persistent var email: String) {
}
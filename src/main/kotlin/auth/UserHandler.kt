package org.example.auth

import org.bson.types.ObjectId
import org.example.auth.util.TokenUtil
import org.example.entity.User
import org.example.repository.UserRepository
import org.mindrot.jbcrypt.BCrypt

class UserHandler(private val userRepository: UserRepository) {

    suspend fun register(username: String, password: String, email: String, role: String = "regular"): Boolean {
        val usernameExists = userRepository.findByField("username", username).isNotEmpty()
        val emailExists = userRepository.findByField("email", email).isNotEmpty()

        if (!usernameExists && !emailExists) {
            val result = userRepository.insertOne(
                User(
                    ObjectId(),
                    username,
                    BCrypt.hashpw(password, BCrypt.gensalt()),
                    email,
                    role,
                    "https://i.imgur.com/RcMEPk3.jpeg"
                )
            )
            return true
        }
        return false
    }

    suspend fun login(usernameOrEmail: String, rawPassword: String): String? {
        val user = if (usernameOrEmail.contains("@")) userRepository.findByField("email", usernameOrEmail).firstOrNull()
            else userRepository.findByField("username", usernameOrEmail).firstOrNull()
        val correctPassword = user?.let { BCrypt.checkpw(rawPassword, user.hashedPassword) }
        return if (correctPassword == true) TokenUtil().generateToken(user.id.toString()) else null
    }
}
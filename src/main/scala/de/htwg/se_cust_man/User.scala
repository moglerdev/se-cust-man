package de.htwg.se_cust_man

enum Role {
    case Admin
    case User
}

case class User(username: String, password: String, name: String, email: String, role: Role)


def checkCredentials(username: String, password: String, users: List[User]): Boolean = {
    users.exists(u => u.username == username && u.password == password)
}

def login(username: String, password: String, users: List[User]): User = {
    users.find(u => u.username == username && u.password == password).get
}

def deleteUser(user: User, users: List[User]) : List[User] = {
    users.filter(p => p.username != user.username)
}

def setUser(user: User, users: List[User]) : List[User] = {
    val res = deleteUser(user, users);
    res.appended(user);
}
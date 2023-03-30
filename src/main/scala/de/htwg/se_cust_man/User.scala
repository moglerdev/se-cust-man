package de.htwg.se_cust_man

enum Role {
    case Admin
    case User
}

case class User(username: String, password: String, name: String, email: String, role: Role)

val __users =  List(
    User("moglerdev", "password", "Christopher Mogler", "ch121mog@htwg-konstanz.de", Role.Admin),
    User("I0n1y", "password", "Dennis Schulze", "de121sch@htwg-konstanz.de", Role.User)
)

def checkCredentials(username: String, password: String, users: List[User]): Boolean = {
    users.exists(u => u.username == username && u.password == password)
}

def login(username: String, password: String, users: List[User]): User | false = {
    users.find(u => u.username == username && u.password == password) match {
        case Some(user) => user
        case None => false
    }
}

def deleteUser(user: User, users: List[User]) : List[User] = {
    users.filter(p => p.username != user.username)
}

def setUser(user: User, users: List[User]) : List[User] = {
    val res = deleteUser(user, users);
    res.appended(user);
}
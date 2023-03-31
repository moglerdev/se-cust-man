package de.htwg.se_cust_man

import java.util.Date


val __customers = List(
    Customer(51373, "Johann Carl Friedrich", "Gauß", new Date(1777, 4, 30), "+49 7836 2036",
    "johann.carl.friedrich@gauss.de",
    "Albanikirchhof 1A, 37073 Göttingen")
);


val __users =  List(
    User("moglerdev", "password", "Christopher Mogler", "ch121mog@htwg-konstanz.de", Role.Admin),
    User("I0n1y", "password", "Dennis Schulze", "de121sch@htwg-konstanz.de", Role.User)
)
@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(
                name = "findUser",
                query = "select u from User u"
        )
})

package caveatemptorlite2h;
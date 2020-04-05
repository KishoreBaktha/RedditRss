package com.example.redditrss.entry

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(name = "author")
class Author() : Serializable {

    @field:Element(name = "name") var name: String? = null
    @field:Element(name = "uri") var uri: String? = null

    constructor(name: String, uri: String) : this() {
        this.name = name
        this.uri = uri
    }

    override fun toString(): String {
        return "Author {" + "name = " + name +
                "uri = " + uri + "}"
    }
}
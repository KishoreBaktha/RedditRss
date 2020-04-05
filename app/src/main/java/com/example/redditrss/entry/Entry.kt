package com.example.redditrss.entry

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(name = "entry", strict = false)
class Entry() : Serializable {

    @field:Element(name = "content") var content: String? = null
    @field:Element(name = "author", required = false) var author: Author? = null
    @field:Element(name = "title") var title: String? = null
    @field:Element(name = "updated") var updated: String? = null
    @field:Element(name = "id") var id: String? = null

    constructor(content: String, author: Author, title: String, updated: String, id: String) : this() {

        this.content = content
        this.author = author
        this.title = title
        this.updated = updated
        this.id = id
    }

    override fun toString(): String {
        return "Entry {" + "content = " + content +
                "author = " + author +
                "title = " + title +
                "updated = " + updated +
                "id = " + id + "}"
    }
}
package com.example.redditrss.model

import com.example.redditrss.entry.Entry
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(name = "feed", strict = false)  //skipping some tags
class Feed : Serializable {

    @field:Element(name = "icon")
    var icon: String? = null
    @field:Element(name = "id")
    var id: String? = null
    @field:Element(name = "logo")
    var logo: String? = null
    @field:Element(name = "title")
    var title: String? = null
    @field:Element(name = "updated")
    var updated: String? = null
    @field:Element(name = "subtitle")
    var subtitle: String? = null

    @field:ElementList(inline = true, name = "entry")  //inline since the entry elements ae not enlcosed wtihin a parent element list
    var entries: List<Entry>? = null

    override fun toString(): String {
        return "Feed: [Entries : " + entries + " ]"
    }
}
package com.najed.top10downloaderapp.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(name = "entry", strict = false)
class Entry @JvmOverloads constructor(

    @field:Element(name = "title")
    @param:Element(name = "title")
    var title: String? = null

    ): Serializable
package vn.dv.annotationclass.testannotation

import vn.dv.annotationclass.annotation.Fancy

@Fancy
class DemoClassAnno {

    @Fancy
    fun haha() {
        val x = Fancy()
    }
}


class Example(
    @field:Fancy val x: Int
)
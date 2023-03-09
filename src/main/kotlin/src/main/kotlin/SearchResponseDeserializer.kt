package src.main.kotlin

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class SearchResponseDeserializer : TypeAdapter<Wrapper>() {
    override fun write(out: JsonWriter?, value: Wrapper?) {
        TODO("Not yet implemented")
    }

    val gson = Gson()
    override fun read(json: JsonReader?): Wrapper {

        val wrapped = Wrapper()

        val declaredFields = wrapped::class.java.declaredFields
        val declaredFieldNames = wrapped::class.java.declaredFields.map { it.name }

        json?.beginObject()
        while (json?.hasNext() == true) {
            val name = json.nextName()
            //NOTE: Here you should check against the UI locale, not by regexp
            if (name.equals("localized") || Regex("^[a-z]{2}_[A-Z]{2}$").matches(name.toString())) {
                //json.beginObject()
                wrapped.localized = gson.fromJson(json, Localized::class.java)
//                json.endObject()
            }else{
                if( declaredFieldNames.contains(name)){
                    val fieldClass = wrapped::class.java.getDeclaredField(name).type

                    val objectField = wrapped::class.java.getDeclaredField(name)
                    objectField.trySetAccessible()
                    objectField.set(wrapped, gson.fromJson(json, fieldClass))
                }
            }
        }

        json?.endObject()
        return wrapped
    }


}

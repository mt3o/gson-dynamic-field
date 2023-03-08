package src.main.kotlin

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class SearchResponseDeserializer : TypeAdapter<SearchResponse>() {
    override fun write(out: JsonWriter?, value: SearchResponse?) {
        TODO("Not yet implemented")
    }

    val gson = Gson()
    override fun read(json: JsonReader?): SearchResponse {
        val response = SearchResponse()
        json?.beginObject()
        while (json?.hasNext() == true) {
            val name = json.nextName()
            //NOTE: Here you should check against the UI locale, not by regexp
            if (name.equals("localized") || Regex("^[a-z]{2}_[A-Z]{2}$").matches(name.toString())) {
                response.localized = gson.fromJson(json, Localized::class.java)
            }else{
                json.skipValue()
            }

        }

        json?.endObject()
        return response
    }


}

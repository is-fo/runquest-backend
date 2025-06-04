package org.example.serializers

import kotlinx.datetime.Instant
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.BsonDateTime
import org.bson.codecs.kotlinx.BsonDecoder
import org.bson.codecs.kotlinx.BsonEncoder

class UnixTimestampSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("unixTimestamp", PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeLong(value.toEpochMilliseconds())
    }
    override fun deserialize(decoder: Decoder): Instant {
        return Instant.fromEpochMilliseconds(decoder.decodeLong())
    }

}

@OptIn(ExperimentalSerializationApi::class)
class InstantAsBsonDateTimeSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("InstantAsBsonDateTime", PrimitiveKind.LONG)
    override fun serialize(encoder: Encoder, value: Instant) {
        when (encoder) {
            is BsonEncoder -> encoder.encodeBsonValue(BsonDateTime(value.toEpochMilliseconds()))
            else -> throw SerializationException("Instant is not supported by ${encoder::class}")
        }
    }
    override fun deserialize(decoder: Decoder): Instant {
        return when (decoder) {
            is BsonDecoder -> Instant.fromEpochMilliseconds(decoder.decodeBsonValue().asDateTime().value)
            else -> throw SerializationException("Instant is not supported by ${decoder::class}")
        }
    }
}
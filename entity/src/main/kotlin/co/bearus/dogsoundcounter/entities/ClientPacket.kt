package co.bearus.dogsoundcounter.entities

data class ClientPacket (
    val packetType: PacketType,
    val payload: Any,
)

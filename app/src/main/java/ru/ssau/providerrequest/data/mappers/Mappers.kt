package ru.ssau.providerrequest.data.mappers

import ru.ssau.providerrequest.data.entity.Data
import ru.ssau.providerrequest.data.entity.Home

fun List<Data>.toDto() = map { it.toDto() }

fun Data.toDto() = Data(
    client = client,
    comment = comment,
    date_time = date_time,
    home = home.toDto(),
    id = id,
    status = status,
    tariff = tariff,
    account_id = account_id
)

fun Home.toDto(): Home {
    val yyyy = updated_at.subSequence(0, 4)
    val mm = updated_at.subSequence(5, 7)
    val dd = updated_at.subSequence(8, 10)
    val hh = updated_at.subSequence(11, 13)
    val mmm = updated_at.subSequence(14, 16)
    return Home(
        address = address,
        comment = comment,
        coords = coords,
        created_at = created_at,
        email_manager = email_manager,
        id = id,
        phone_manager = phone_manager,
        updated_at = "$yyyy-$mm-$dd $hh:$mmm"
    )
}
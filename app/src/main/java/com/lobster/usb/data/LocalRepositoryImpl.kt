package com.lobster.usb.data

import com.lobster.usb.domain.interfaces.LocalRepository
import io.objectbox.BoxStore

class LocalRepositoryImpl(private val boxStore: BoxStore) : LocalRepository {

}
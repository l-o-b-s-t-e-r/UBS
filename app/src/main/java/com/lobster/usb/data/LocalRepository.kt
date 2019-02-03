package com.lobster.usb.data

import com.lobster.usb.domain.interfaces.ILocalRepository
import io.objectbox.BoxStore

class LocalRepository(private val boxStore: BoxStore) : ILocalRepository {

}
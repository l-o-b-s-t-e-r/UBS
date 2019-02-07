package com.lobster.usb.presentation.di

import com.lobster.usb.presentation.ui.symbol_details.SymbolDetailsFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [SymbolDetailsModule::class])
interface SymbolDetailsComponent {

    fun inject(fragment: SymbolDetailsFragment)

}

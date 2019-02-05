package com.lobster.usb.presentation.di

import com.lobster.usb.presentation.ui.symbols_list.SymbolsListFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [SymbolsListModule::class])
interface SymbolsListComponent {

    fun inject(fragment: SymbolsListFragment)

}

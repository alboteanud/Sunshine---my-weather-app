package com.craiovadata.android.sunshine.data.network

import com.craiovadata.android.sunshine.ui.models.WebcamEntry

/**
 * Weather response from the backend. Contains the weather forecasts.
 */
internal class WebcamResponse(val webcams: Array<WebcamEntry>)
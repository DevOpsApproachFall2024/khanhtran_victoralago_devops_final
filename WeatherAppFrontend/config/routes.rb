Rails.application.routes.draw do
  root "weather#index"
  get "weather/:city", to: "weather#show", as: "weather"
  post "fetch_weather", to: "weather#fetch", as: "fetch_weather"

  get "tony", to: "static#tony", as: "tony_video"
end

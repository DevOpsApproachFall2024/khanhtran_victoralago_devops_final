require "countries"

class WeatherController < ApplicationController
  def index
    # @countries = ISO3166::Country.all.map { |country| country.common_name }
    @countries = ISO3166::Country.all.map(&:common_name)
  end

  def fetch
    city = params[:city]
    redirect_to weather_path(city) # This redirects to the show action with the city as a URL segment
  end

  def show
    city = params[:city]
    @weather_data = fetch_weather_data(city)
  end

  private

  def fetch_weather_data(city)
    backend_url = ENV.fetch("BACKEND_URL", "http://localhost:8080") # Use BACKEND_URL env var
    encoded_city = CGI.escape(city)
    response = HTTParty.get("#{backend_url}/weather/#{encoded_city}")
    response.parsed_response if response.success?
  rescue HTTParty::Error => e
    logger.error "HTTParty::Error: #{e.message}"
    nil
  end
end
